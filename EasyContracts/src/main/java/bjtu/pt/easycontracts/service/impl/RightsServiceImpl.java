package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.mapper.RightsMapper;
import bjtu.pt.easycontracts.mapper.RoleRightMapper;
import bjtu.pt.easycontracts.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static bjtu.pt.easycontracts.utils.Global.FAIL;
import static bjtu.pt.easycontracts.utils.Global.SUCCESS;

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
    @Autowired
    private RightsMapper rightsMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public int allocationRights(int userId, List<Rights> rights) {
        RoleRightExample roleRightExample = new RoleRightExample();
        roleRightExample.createCriteria().andUseridEqualTo(userId);//类似于where后面的内容
        roleRightMapper.deleteByExample(roleRightExample);
        User user = userService.getUserById(userId);

        for(int i=0;i<rights.size();i++){
            RoleRight roleRight=new RoleRight();
            roleRight.setUserid(userId);
            roleRight.setRightid(rights.get(i).getRightid());
            roleRightMapper.insert(roleRight);
        }

        /* 向用户发送邮件，告知已获得权限 */
        emailService.sendSimpleMail(user.getEmail() , "Allocate Right" , "You have got rights, remember to check!");

        return SUCCESS;
    }

    @Override
    public int allocationRights(String username, List<Rights> rights) {
        int userId=userService.getUserByUserName(username).getUserid();
        if(allocationRights(userId,rights)==1){
            /* 向用户发送邮件，告知已获得权限 */
            User user = userService.getUserByUserName(username);
            emailService.sendSimpleMail(user.getEmail() , "Allocate Right" , "You have got rights, remember to check!");
            return SUCCESS;
        }
        else{
            return FAIL;
        }
    }

    @Override
    public int allocationRights(int userId, Rights rights) {
        RoleRight roleRight=new RoleRight();
        roleRight.setUserid(userId);
        roleRight.setRightid(rights.getRightid());
        roleRightMapper.insert(roleRight);
        /* 向用户发送邮件，告知已获得权限 */
        User user = userService.getUserById(userId);
        emailService.sendSimpleMail(user.getEmail() , "Allocate Right" , "You have got rights, remember to check!");
        return SUCCESS;
    }

    @Override
    public int allocationRights(String username, Rights rights) {
        int userId=userService.getUserByUserName(username).getUserid();
        if(allocationRights(userId, rights)==1){
            /* 向用户发送邮件，告知已获得权限 */
            User user = userService.getUserByUserName(username);
            emailService.sendSimpleMail(user.getEmail() , "Allocate Right" , "You have got rights, remember to check!");
            return SUCCESS;
        }
        else{
            return FAIL;
        }
    }

    @Override
    public int deleteRights(int userId) {
        RoleRightExample example = new RoleRightExample();
        example.createCriteria().andUseridEqualTo(userId);
        int lines;
        lines = roleRightMapper.deleteByExample(example);
        /* 向用户发送邮件，告知已获得权限 */
        if (lines != 0)
        {
            User user = userService.getUserById(userId);
            emailService.sendSimpleMail(user.getEmail() , "Allocate Right" , "Your rights have been deleted, remember to check!");
        }

        return lines;
        // TODO: 错误处理
    }

    @Override
    public List<Rights> listRights(int userId) {
        RoleRightExample example = new RoleRightExample();
        example.createCriteria().andUseridEqualTo(userId);
        List<RoleRight> gotRoleRights = roleRightMapper.selectByExample(example);

        List<Rights> gotRights = new ArrayList<>();

        for (RoleRight eachRoleRights: gotRoleRights)
        {
            RightsExample rightsExample = new RightsExample();
            rightsExample.createCriteria().andRightidEqualTo(eachRoleRights.getRightid());
            List<Rights> tempRights;
            tempRights = rightsMapper.selectByExample(rightsExample);
            gotRights.add(tempRights.get(0));
        }
        return gotRights;
    }

    @Override
    public Rights ifUserHasRights(int userId, int rightsId) {
        RoleRightExample example = new RoleRightExample();
        example.createCriteria().andUseridEqualTo(userId);
        List<RoleRight> gotRoleRights = roleRightMapper.selectByExample(example);
        for (RoleRight i: gotRoleRights)
        {
            if (i.getRightid() == rightsId)
            {
                return rightsMapper.selectByPrimaryKey(rightsId);
            }
        }
        return null;
    }

    @Override
    public List<Rights> createRightList(List<Integer> rights) {
        List<Rights> result = new ArrayList<>();
        for (int i = 1; i <= rights.size(); i++) {
            if (rights.get(i-1)==1){
                Rights right = rightsMapper.selectByPrimaryKey(i);
                if (right!=null){
                    result.add(right);
                }
            }
        }
        return result;
    }
}
