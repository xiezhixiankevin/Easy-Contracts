package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.CustomerService;
import bjtu.pt.easycontracts.service.RightsService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * <Description> SkipController
 *  负责单纯的页面跳转，即无需处理数据的直接跳转，需要处理数据的跳转不在此类中声明
 * @author 26802
 * @version 1.0
 * @ClassName SkipController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */

@Controller
public class SkipController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private RightsService rightsService;

    //跳转到注册页面，
    @GetMapping("/toRegister")
    public String toRegister(){
        return "loginup";
    }

    //跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "loginin";
    }

    //跳转到首页
    @GetMapping("/")
    public String toIndex(){
        return "index";
    }

    //跳转到me页面
    @GetMapping("/toMe")
    public String toMe(){
        return "me";
    }

    //跳转到select用户页面
    @GetMapping("/toSelectUser")
    public String toSelectUser(){
        return "user/select_users";
    }

    //跳转到create用户页面
    @GetMapping("/toCreateUser")
    public String toCreateUser(){
        return "user/create_users";
    }

    //跳转到modify用户页面
    @GetMapping("/toModifyUser")
    public String toModifyUser(){
        return "user/modify_users";
    }

    //跳转到查询需要分配合同页面
    @GetMapping("/toselectCNeedAssign")
    public String toselectCNeedAssign(Model model){
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/select_contract_needAllocation";
    }

    //跳转到权限分配页面
    @GetMapping("/toRights/{username}")
    public String toRights(@PathVariable("username")String username, Model model,HttpSession session){
        User nowUser = (User)session.getAttribute("nowUser");
        if (!nowUser.ifHasRight(Global.PERMISSION_ASSIGN_PERMISSIONS)){
            return "error/noRight";
        }
        User userToAssign = userService.getUserByUserName(username);
        userToAssign.setUserRights(rightsService.listRights(userToAssign.getUserid()));
        model.addAttribute("userToAssign",userToAssign);
        return "permission/assign_permissions";
    }

    //跳转到起草合同页面如果
    @GetMapping("/toDraft")
    public String toDraft(HttpSession session,Model model){
        User nowUser = (User)session.getAttribute("nowUser");
        if (!nowUser.ifHasRight(Global.PERMISSION_DRAFT_CONTRACT)){
            return "error/noRight";
        }
        model.addAttribute("customers",customerService.listCustomerSelective(null));
        return "contract/draft";
    }

}
