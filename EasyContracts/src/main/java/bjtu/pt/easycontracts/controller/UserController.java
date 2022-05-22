package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.logic.Code;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.CodeService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
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
    @Autowired
    private UserService userService;

    /*
    * 注册请求，注册成功跳转到登录页面;失败重定向(redirect:xxx)到注册页面，并给出相应提示
    * */
    @PostMapping("/register")
    @ResponseBody
    public ReturnObject<User> register(User user, @RequestParam("code")String codeValue){

        String email = user.getEmail();
        int checkCode = codeService.checkCode(new Code(email, codeValue), Global.REGISTER);
        if (checkCode == Global.SUCCESS)
        {
            User user1 = userService.registerUser(user);
            if(user1==null){
                return new ReturnObject<>(Global.FAIL, null);
            }else {

            }

            return new ReturnObject<>(Global.SUCCESS, user1);
        } else {
            return new ReturnObject<>(Global.FAIL, null);
        }
    }

    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/login/{username}/{password}")
    public User loginUser(@PathVariable("username")String username,
                          @PathVariable("password")String password){
        User user = userService.loginUser(username,password);
        if(user==null){
            return null;
        }else
            return user;
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
