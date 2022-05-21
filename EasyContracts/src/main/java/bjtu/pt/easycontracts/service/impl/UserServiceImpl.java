package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.pojo.table.User;
import bjtu.pt.easycontracts.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{


    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
       /* for(int i=0;i<listUser().size();i++){
            User user=listUser().get(i);
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    return user;
                }
            }
        }*/
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