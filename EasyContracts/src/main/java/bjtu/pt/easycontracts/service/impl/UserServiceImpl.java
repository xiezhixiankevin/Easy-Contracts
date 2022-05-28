package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.mapper.RoleRightMapper;
import bjtu.pt.easycontracts.mapper.UserMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <Description> UserServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName UserServiceImpl
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContractProcessMapper contractProcessMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleRightMapper roleRightMapper;

    @Override
    public User registerUser(User user) {
        // 判断是否已存在该用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> userList = userMapper.selectByExample(userExample);
        if (!userList.isEmpty())
        {
            return null;
        }

        // 否则注册为新的用户
        userMapper.insert(user);

        /* 成功后告知对方已成功注册 */
        emailService.sendSimpleMail(user.getEmail() , "Register Notice" , "Register successfully! Welcome to use our products, if you have any " +
                "comments, please feel free to feedback, we will actively improve, thank you.");

        return user;
    }

    @Override
    public User loginUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPasswordEqualTo(password).andUsernameEqualTo(username);
        List<User> users =userMapper.selectByExample(userExample);
        if(users!=null){
            if(users.size()==1) return users.get(0);
            else return null;
        }
        else return null;
    }

    @Override
    public String retrievePassword(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users!=null){
            if (users.size()!=0) return users.get(0).getPassword();
            else return null;
        }
        return null;
    }

    @Override
    public List<User> listUser(int pn) {
        PageHelper.startPage(pn,5); //每页显示5个数据
        return userMapper.selectByExample(null);
    }

    @Override
    public List<User> listUserSelective(User user, int pn) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (user.getUsername()!=null&&user.getUsername().length()!=0){
            criteria.andUsernameLike("%"+user.getUsername()+"%");
        }
        if(user.getUserdescription()!=null&&user.getUserdescription().length()!=0){
            criteria.andUserdescriptionLike("%"+user.getUserdescription()+"%");
        }
        PageHelper.startPage(pn,5); //每页显示5个数据
        return userMapper.selectByExample(userExample);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByUserName(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean ifExistUser(String username)
    {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean ifExistUser(Integer id)
    {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(id);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int deleteUser(Integer userID){
        return userMapper.deleteByPrimaryKey(userID);
    }

    @Override
    public List<User> getUsersAboutContract(int contractId)
    {
        List<User> users = new ArrayList<>();
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        List<ContractProcess> contractProcesses;

        /* 通过合同id在contractProcess表中找到所有的合同操作员的记录 */
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);
        contractProcesses = contractProcessMapper.selectByExample(contractProcessExample);

        /* 通过找到的记录遍历user表，获取这些操作员的全部信息 */
        for (ContractProcess contractProcess : contractProcesses)
        {
            boolean isExist = false;
            int userId = contractProcess.getUserid();

            /* 如果该记录对应的操作员的相关信息还没有被存储到users中，则将其信息检索出来并存储 */
            if (users.size() != 0)
            {
                for (User user : users)
                {
                    if (user.getUserid() == userId)
                    {
                        isExist = true;
                    }
                }
            }

            if (!isExist)
            {
                User user = userMapper.selectByPrimaryKey(userId);
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public Map<Integer,List<User>> getUserListByRightsForAssignContract() {
        // 会签
        List<User> countersignUsers = new ArrayList<>();
        // 查询符合条件的 RoleRight
        List<RoleRight> roleRights = new ArrayList<>();
        RoleRightExample example = new RoleRightExample();
        example.createCriteria().andRightidEqualTo(Global.PERMISSION_COUNTERSIGN_CONTRACT);
        roleRights = roleRightMapper.selectByExample(example);
        for (RoleRight eachRoleRight : roleRights) {
            // 通过 RoleRight 获得 User
            User temp = userMapper.selectByPrimaryKey(eachRoleRight.getUserid());
            countersignUsers.add(temp);
        }

        // 审批
        List<User> examUsers = new ArrayList<>();
        roleRights = new ArrayList<>();
        example = new RoleRightExample();
        example.createCriteria().andRightidEqualTo(Global.PERMISSION_APPROVE_CONTRACT);
        roleRights = roleRightMapper.selectByExample(example);
        for (RoleRight eachRoleRight : roleRights) {
            // 通过 RoleRight 获得 User
            User temp = userMapper.selectByPrimaryKey(eachRoleRight.getUserid());
            examUsers.add(temp);
        }

        // 签订
        List<User> signUsers = new ArrayList<>();
        roleRights = new ArrayList<>();
        example = new RoleRightExample();
        example.createCriteria().andRightidEqualTo(Global.PERMISSION_SIGN_CONTRACT);
        roleRights = roleRightMapper.selectByExample(example);
        for (RoleRight eachRoleRight : roleRights) {
            // 通过 RoleRight 获得 User
            User temp = userMapper.selectByPrimaryKey(eachRoleRight.getUserid());
            signUsers.add(temp);
        }

        // Map<Integer, List<Contract>> listConTractUserNeedDeal=new HashMap<Integer, List<Contract>>();
        Map<Integer, List<User>> listUsers = new HashMap<>();

        listUsers.put(Global.PERMISSION_COUNTERSIGN_CONTRACT, countersignUsers);
        listUsers.put(Global.PERMISSION_APPROVE_CONTRACT, examUsers);
        listUsers.put(Global.PERMISSION_SIGN_CONTRACT, signUsers);


        return listUsers;
    }
}