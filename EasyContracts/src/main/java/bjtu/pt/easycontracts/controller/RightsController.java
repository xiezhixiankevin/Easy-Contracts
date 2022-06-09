package bjtu.pt.easycontracts.controller;

import java.util.*;

import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.LogService;
import bjtu.pt.easycontracts.service.RightsService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static bjtu.pt.easycontracts.utils.Global.PERMISSION_ASSIGN_PERMISSIONS;

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

    //注入配置文件中配置的信息——>from
    @Value("${AdminCode}")
    private String Code;

    @PostMapping("/allocationRights")
    @ResponseBody
    public String allocationRights(@RequestBody List<Integer> rights,
                                   @RequestParam("username") String username,
                                   HttpSession session){
            if (userService.ifExistUser(username)){
                List<Rights> rightList = rightsService.createRightList(rights);
                int result = rightsService.allocationRights(username, rightList);

                if (result != Global.SUCCESS){
                    //不能删除权限
                    return String.valueOf(Global.ERROR);
                }
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

    @PostMapping("/toBeAdmin")
    @ResponseBody
    public String toBeAdmin(@RequestParam("code")String code,
                                   HttpSession session){

        User nowUser = (User) session.getAttribute("nowUser");
        if (userService.ifExistUser(nowUser.getUsername()) && Code.equals(code)){
            List<Integer> rights = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                rights.add(1);
            }
            List<Rights> rightList = rightsService.createRightList(rights);
            int result = rightsService.allocationRights(nowUser.getUsername(), rightList);
            User userOperated = userService.getUserByUserName(nowUser.getUsername());
            nowUser.setUserRights(rightsService.listRights(nowUser.getUserid()));
            session.setAttribute("nowUser",nowUser);
            /* 写入日志 */
            logService.addType3Log(userOperated.getUserid() , nowUser.getUserid() , Global.ASSIGN_PERMISSION_LOG);
            return String.valueOf(Global.SUCCESS);
        }
        return String.valueOf(Global.FAIL);
    }

}
