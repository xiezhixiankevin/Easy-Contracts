package bjtu.pt.easycontracts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    //跳转到权限分配页面
    @GetMapping("/toRights/{username}")
    public String toRights(@PathVariable("username")String username, Model model){
        model.addAttribute("username",username);
        return "permission/assign_permissions";
    }

}
