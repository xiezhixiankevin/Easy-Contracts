package bjtu.pt.easycontracts.service.impl;


import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.mapper.RoleRightMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <Description> RightsServiceImpl
 *
 * @author 26802
 * @version 1.0
 * @ClassName RightsServiceImpl
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */
@Service
public class RightsServiceImpl implements RightsService {

    @Autowired
    private RoleRightMapper roleRightMapper;
    @Autowired
    private UserService userService;

    @Override
    public int allocationRights(int userId, List<Rights> rights) {
        RoleRightExample roleRightExample = new RoleRightExample();
        roleRightExample.createCriteria().andUseridEqualTo(userId);//类似于where后面的内容
        roleRightMapper.deleteByExample(roleRightExample);

        for(int i=0;i<rights.size();i++){
            RoleRight roleRight=new RoleRight();
            roleRight.setUserid(userId);
            roleRight.setRightid(rights.get(i).getRightid());
            roleRightMapper.insert(roleRight);
        }
        return 1;
    }

    @Override
    public int allocationRights(String username, List<Rights> rights) {
        int userId=userService.getUserByUserName(username).getUserid();
        if(allocationRights(userId,rights)==1){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public int allocationRights(int userId, Rights rights) {
        RoleRight roleRight=new RoleRight();
        roleRight.setUserid(userId);
        roleRight.setRightid(rights.getRightid());
        roleRightMapper.insert(roleRight);
        return 1;
    }

    @Override
    public int allocationRights(String username, Rights rights) {
        int userId=userService.getUserByUserName(username).getUserid();
        if(allocationRights(userId, rights)==1){
            return 1;
        }
        else{
            return 0;
        }
    }

    @Override
    public int deleteRights(int userId) {
        return 0;
    }

    @Override
    public List<Rights> listRights(int userId) {
        return null;
    }

    @Override
    public Rights ifUserHasRights(int userId, int rightsType) {
        return null;
    }

    @Override
    public List<Rights> createRightList(List<Integer> rights) {
        return null;
    }
}
