package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.UserMapper;
import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.pojo.table.UserExample;
import bjtu.pt.easycontracts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public List<User> listUser() {
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return null;
    }

    @Override
    public boolean ifExistUser(String username) {
        return false;
    }

    @Override
    public boolean ifExistUser(Integer id) {
        return false;
    }
}