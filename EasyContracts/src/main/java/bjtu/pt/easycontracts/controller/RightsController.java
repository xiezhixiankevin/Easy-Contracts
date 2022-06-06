package bjtu.pt.easycontracts.controller;

import java.util.*;

import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.LogService;
import bjtu.pt.easycontracts.service.RightsService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <Description> RightsController
 *
 * @author 26802
 * @version 1.0
 * @ClassName RightsController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@Controller
@RequestMapping("/rights")
public class RightsController {

    @Autowired
    private RightsService rightsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @PostMapping("/allocationRights")
    @ResponseBody
    public String allocationRights(@RequestBody List<Integer> rights,
                                   @RequestParam("username") String username,
                                   HttpSession session){
            if (userService.ifExistUser(username)){
                List<Rights> rightList = rightsService.createRightList(rights);
                rightsService.allocationRights(username,rightList);
                User nowUser = (User) session.getAttribute("nowUser");
                User userOperated = userService.getUserByUserName(username);
                nowUser.setUserRights(rightsService.listRights(nowUser.getUserid()));
                session.setAttribute("nowUser",nowUser);
                /* 写入日志 */
                logService.addType3Log(userOperated.getUserid() , nowUser.getUserid() , Global.ASSIGN_PERMISSION_LOG);
                return String.valueOf(Global.SUCCESS);
            }
            return String.valueOf(Global.FAIL);
    }
}
