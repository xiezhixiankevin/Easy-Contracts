package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.Administrator;
import bjtu.pt.easycontracts.pojo.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <Description> UserController
 *
 * @author 26802
 * @version 1.0
 * @ClassName UserController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /*
    * 注册请求，注册成功跳转到登录页面;失败重定向(redirect:xxx)到注册页面，并给出相应提示
    * */
    @PostMapping("/registerOperator")
    public String registerOperator(Operator operator){
        return null;
    }

    /*
     * 注册请求，注册成功跳转到登录页面;失败重定向(redirect:xxx)到注册页面，并给出相应提示
     * */
    @PostMapping("/registerAdministrator")
    public String registerAdministrator(Administrator administrator){
        return null;
    }

    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/loginOperator/{username}/{password}")
    public String loginOperator(@PathVariable("username")String username,
                                @PathVariable("password")String password){
        return null;
    }

    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/loginAdministrator/{username}/{password}")
    public String loginAdministrator(@PathVariable("username")String username,
                                     @PathVariable("password")String password){
        return null;
    }

    /*
     *找回密码请求，用户输入的验证码正确跳转到显示密码页面将密码展示给用户;否则重定向到当前页面，并给出提示:验证码不正确
     */
    @GetMapping("/retrievePassword/{username}/{code}")
    public String retrievePassword(@PathVariable("username")String username,
                                   @PathVariable("code")String code){
        return null;
    }

}
