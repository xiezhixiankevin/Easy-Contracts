package bjtu.pt.easycontracts.service;


import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.pojo.table.User;
import java.util.List;
import java.util.Map;

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

    /*找回密码部分
    * xzx
    * */
    String retrievePassword(String username); //根据用户名查询密码并返回，调用此方法的前提是验证码输入正确

    /*通用部分*/
    List<User> listUser(int pn); //获取数据库中的所有用户 fbf
    List<User> listUser(); //获取数据库中的所有用户
    List<User> listUserSelective(User user,int pn); //xzx
    User getUserById(Integer id); //根据id获取用户 fbf
    User getUserByUserName(String username); //xzx
    boolean ifExistUser(String username); //wj
    boolean ifExistUser(Integer id); //wj
    int deleteUser(Integer userID);

    /* 传入合同id，得到所有该合同操作员的user对象 */
    List<User> getUsersAboutContract(int contractId);

    int updateUser(int userId, User user);

    //将数据库中的用户按权限进行返回()//只需要会签，审批，签订
    Map<Integer,List<User>> getUserListByRightsForAssignContract();
}
