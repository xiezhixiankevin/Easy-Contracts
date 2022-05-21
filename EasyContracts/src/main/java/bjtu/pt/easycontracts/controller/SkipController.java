package bjtu.pt.easycontracts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/")
    public String toRegister(){
        return "loginup";
    }

    //跳转到登录页面
    @GetMapping("/toLogin")
    public String toLogin(){
        return "loginin";
    }

}
