package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.UserMapper;
import bjtu.pt.easycontracts.pojo.table.User;
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
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
        return null;
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

}