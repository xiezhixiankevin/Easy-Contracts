package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Rights;

import java.util.List;
import java.util.Map;

/**
 * <Description> RoleService
 *
 * @author 26802
 * @version 1.0
 * @ClassName RoleService
 * @taskId
 * @see bjtu.pt.easycontracts.service.impl
 */
public interface RightsService {

    //分配一组权限，可以调用allocationRights方法(考虑到前端分配和修改时是选择框一次性提交，而不是一个个删或者改，所以每次分配之前记得把这个人的所有已有的角色删完再分配)
    int allocationRights(int userId, List<Rights> rights);//zxc
    int allocationRights(String username, List<Rights> rights);//zxc

    //分配一个权限
    int allocationRights(int userId,Rights rights);//zxc
    int allocationRights(String username,Rights rights);//zxc

    //删除某个用户的所有权限
    int deleteRights(int userId);//hcb

    //查询某个用户的权限
    List<Rights> listRights(int userId);//hcb

    //判断用户是否有某某权限
    Rights ifUserHasRights(int userId,int rightsType);//hcb

    //根据list的值返回一个List<Rights>
    List<Rights> createRightList(List<Integer> rights);//xzx

    //判断能否删除某个权限
    int checkDelete(int userId,int rightId);
}
