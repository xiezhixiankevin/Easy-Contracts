package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.mapper.RightsMapper;
import bjtu.pt.easycontracts.mapper.RoleRightMapper;
import bjtu.pt.easycontracts.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;

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
    private ContractProcessMapper contractProcessMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public int allocationRights(int userId, List<Rights> rights) {
        //检查是否能删除权限
        RoleRightExample roleRightExample = new RoleRightExample();
        roleRightExample.createCriteria().andUseridEqualTo(userId);//类似于where后面的内容
        List<RoleRight> roleRightList=new ArrayList<>();
        roleRightList=roleRightMapper.selectByExample(roleRightExample);//用户已有的权限
        int flagSELECT=0;//合同查询
        int flagFINALIZE=0;//合同定稿
        int flagCOUNTERSIGN=0;//合同会签
        int flagAPPROVE=0;//合同审批
        int flagSIGN=0;//合同签订
        //修改后的权限
        for(int i=0;i<rights.size();i++){
            if(rights.get(i).getRightid()==PERMISSION_SELECT_CONTRACT){
                flagSELECT=1;
            }
            if(rights.get(i).getRightid()==PERMISSION_FINALIZE_CONTRACT){
                flagFINALIZE=1;
            }
            if(rights.get(i).getRightid()==PERMISSION_COUNTERSIGN_CONTRACT){
                flagCOUNTERSIGN=1;
            }
            if(rights.get(i).getRightid()==PERMISSION_APPROVE_CONTRACT){
                flagAPPROVE=1;
            }
            if(rights.get(i).getRightid()==PERMISSION_SIGN_CONTRACT){
                flagSIGN=1;
            }
        }
        //修改前的权限
        for(int i=0;i<roleRightList.size();i++){
            if(checkDelete(userId,roleRightList.get(i).getRightid())==PERMISSION_SELECT_CONTRACT){
                if(flagSELECT==0)
                    return PERMISSION_SELECT_CONTRACT;
            }
            else if(checkDelete(userId,roleRightList.get(i).getRightid())==PERMISSION_FINALIZE_CONTRACT){
                if(flagFINALIZE==0)
                    return PERMISSION_FINALIZE_CONTRACT;
            }
            else if(checkDelete(userId,roleRightList.get(i).getRightid())==PERMISSION_COUNTERSIGN_CONTRACT){
                if(flagCOUNTERSIGN==0)
                    return PERMISSION_COUNTERSIGN_CONTRACT;
            }
            else if(checkDelete(userId,roleRightList.get(i).getRightid())==PERMISSION_APPROVE_CONTRACT){
                if(flagAPPROVE==0)
                    return PERMISSION_APPROVE_CONTRACT;
            }
            else if(checkDelete(userId,roleRightList.get(i).getRightid())==PERMISSION_SIGN_CONTRACT){
                if(flagSIGN==0)
                    return PERMISSION_SIGN_CONTRACT;
            }
        }


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
        if(allocationRights(userId,rights)==SUCCESS){
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
        if(allocationRights(userId, rights)==SUCCESS){
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
        //检查能否删除
        RoleRightExample roleRightExample = new RoleRightExample();
        roleRightExample.createCriteria().andUseridEqualTo(userId);//类似于where后面的内容
        List<RoleRight> roleRightList=new ArrayList<>();
        roleRightList=roleRightMapper.selectByExample(roleRightExample);//用户已有的权限
        for(int i=0;i<roleRightList.size();i++){
            if(checkDelete(userId,roleRightList.get(i).getRightid())!=SUCCESS)
                return checkDelete(userId,roleRightList.get(i).getRightid());
        }

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

    @Override
    public int checkDelete(int userId,int rightId)
    {
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        contractProcessExample.createCriteria().andUseridEqualTo(userId);
        List<ContractProcess> contractProcessList=new ArrayList<>();
        contractProcessList=contractProcessMapper.selectByExample(contractProcessExample);
        for(int i=0;i<contractProcessList.size();i++) {
            if (contractProcessList.get(i).getUserid() == userId) {
                if (contractProcessList.get(i).getType() == COUNTERSIGN && rightId==PERMISSION_COUNTERSIGN_CONTRACT) {
                    return PERMISSION_COUNTERSIGN_CONTRACT;
                }
                if (contractProcessList.get(i).getType() == FINALIZE && rightId==PERMISSION_FINALIZE_CONTRACT) {
                    return PERMISSION_FINALIZE_CONTRACT;
                }
                if (contractProcessList.get(i).getType() == EXAM && rightId==PERMISSION_APPROVE_CONTRACT) {
                    return PERMISSION_APPROVE_CONTRACT;
                }
                if (contractProcessList.get(i).getType() == SIGN && rightId==PERMISSION_SIGN_CONTRACT) {
                    return PERMISSION_SIGN_CONTRACT;
                }
            }
        }

        //查询合同功能
        if(rightId==PERMISSION_SELECT_CONTRACT){
            for(int i=0;i<contractProcessList.size();i++) {
                if (contractProcessList.get(i).getUserid() == userId){
                    return PERMISSION_SELECT_CONTRACT;
                }
            }
        }
        return SUCCESS;
    }
}
