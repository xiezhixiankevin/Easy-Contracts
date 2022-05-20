package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.User;

import java.util.List;

/**
 * <Description> UserService
 *  与用户有关的业务均在此接口中声明，比如登录，注册等等
 * @author 26802
 * @version 1.0
 * @ClassName UserService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */

public interface UserService {

    /*注册部分*/

    /*
     *注册业务，传入一个对象用于注册,注册成功返回相应对象(带id),否则返回null
    * */
    //注册成为用户
    User registerUser(User user);

    /*登录部分*/

    /*
    * 同样,登陆成功返回用户实体(包含从数据库中查到的所有字段),否则返回null
    * */
    User loginUser(String username,String password);

    /*找回密码部分*/
    String retrievePassword(String username); //根据用户名查询密码并返回，调用此方法的前提是验证码输入正确

    /*通用部分*/
    List<User> listUser(); //获取数据库中的所有用户
    User getUserById(Integer id); //根据id获取用户


}
