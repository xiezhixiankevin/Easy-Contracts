package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.logic.Code;
import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.CodeService;
import bjtu.pt.easycontracts.service.LogService;
import bjtu.pt.easycontracts.service.RightsService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static bjtu.pt.easycontracts.utils.Global.SUCCESS;

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
    @Autowired
    private RightsService rightsService;

    @Autowired
    private LogService logService;

    /*
    * 注册请求，注册成功跳转到登录页面;失败重定向(redirect:xxx)到注册页面，并给出相应提示
    * */
    @PostMapping("/register")
    @ResponseBody
    public ReturnObject<User> register(User user, @RequestParam("code")String codeValue){

        String email = user.getEmail();
        int checkCode = codeService.checkCode(new Code(email, codeValue), Global.REGISTER);
        if (checkCode == SUCCESS)
        {
            User user1 = userService.registerUser(user);
            if(user1==null){
                return new ReturnObject<>(Global.NAME_EXIST, null);
            }
            return new ReturnObject<>(SUCCESS, user1);
        } else {
            return new ReturnObject<>(Global.CODE_ERROR, null);
        }
    }

    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    public ReturnObject<User> loginUser(@PathVariable("username")String username,
                                        @PathVariable("password")String password,
                                        HttpSession session, HttpServletResponse response){
        User user = userService.loginUser(username,password);
        if(user==null){
            return new ReturnObject<>(Global.ERROR, null);
        }else {
            List<Rights> rights = rightsService.listRights(user.getUserid());
            user.setUserRights(rights);
            session.setAttribute("nowUser", user);
            Cookie cookie = new Cookie("id", String.valueOf(user.getUserid()));
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("登录 Cookie saved");
        }

            return new ReturnObject<>(SUCCESS, user);
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

    /*
    *向前端返回用户列表
     */
    @GetMapping("/listUser/{pageNum}")
    @ResponseBody
    public ReturnObject<PageInfo> listUser(User user,
                                             @PathVariable("pageNum")Integer pageNum){

        List<User> userList = userService.listUserSelective(user,pageNum);
        PageInfo pageInfo = new PageInfo(userList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(SUCCESS,pageInfo);

        return result;
    }

    @PostMapping("/delete/{userID}")
    @ResponseBody
    public String deleteUser(@PathVariable("userID")Integer userID , HttpSession session){
        userService.deleteUser(userID);//i返回的是影响的行数，所以=0代表没有这个人，并不表示失败
        rightsService.deleteRights(userID); //把用户的权限一起删除

        /* 添加日志 */
        User user = (User) session.getAttribute("nowUser");
        logService.addType3Log(userID , user.getUserid() , Global.DELETE_USER_LOG);

        return String.valueOf(SUCCESS);
    }

    @GetMapping("/listUserByRight")
    @ResponseBody
    public ReturnObject<Map<Integer, List<User>>> listUserByRight(){
        Map<Integer, List<User>> map = userService.getUserListByRightsForAssignContract();
        return new ReturnObject<>(SUCCESS,map);
    }

    @GetMapping("/modifyUserInfo")
    @ResponseBody
    public String modifyUserInfo(Integer userId, String email,
                                 String username, String password, String description) {
        User newUser = new User();
        newUser.setUserid(userId);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUsername(username);
        newUser.setUserdescription(description);
        userService.updateUser(userId, newUser);
        return "SUCCESS";

    }

}
