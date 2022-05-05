package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.Administrator;
import bjtu.pt.easycontracts.pojo.Operator;
import org.apache.catalina.User;

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
    //注册成为操作员
    Operator registerOperator(Operator user);
    //注册成为管理员
    Administrator registerAdmin(Administrator user);

    /*登录部分*/

    /*
    * 同样,登陆成功返回用户实体(包含从数据库中查到的所有字段),否则返回null
    * */
    Operator loginOperator(String username,String password);
    Administrator loginAdministrator(String username ,String password);

    /*找回密码部分*/
    boolean checkCode(String code); //检查用户输入的验证码与redis中存储的是否一致，一致则调用retrievePassword返回密码
    String retrievePassword(String username); //根据用户名查询密码并返回，调用此方法的前提是验证码输入正确

    /*通用部分*/
    List<Operator> listOperator(); //获取数据库中的所有操作员
    Operator getOperatorById(Integer id); //根据id获取操作员
    List<Administrator> listAdministrator(); //获取数据库中的所有管理员
    Administrator getAdministratorById(Integer id); //根据id获取管理员

}
