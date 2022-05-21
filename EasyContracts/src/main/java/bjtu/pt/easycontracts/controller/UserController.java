package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CodeService codeService;

    /*
    * 注册请求，注册成功跳转到登录页面;失败重定向(redirect:xxx)到注册页面，并给出相应提示
    * */
    @PostMapping("/register")
    @ResponseBody
    public String register(User user, @RequestParam("code")String codeValue){

        return null;
    }

    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/login/{username}/{password}")
    public String loginUser(@PathVariable("username")String username,
                                @PathVariable("password")String password){

        return null;
    }

    /*
     * 发送验证码请求，向用户的邮箱发送验证码
     * */
    @GetMapping("/register/sendCode")
    @ResponseBody
    public String sendCode(@RequestParam("email")String email,@RequestParam("type")int type){
        int result = codeService.sendCode(email, type);
        return String.valueOf(result);
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
