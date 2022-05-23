package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Rights;

import java.util.List;

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
    int allocationRights(int userId, List<Rights> rights);

    //分配一个权限
    int allocationRights(int userId,Rights rights);

    //删除某个用户的所有权限
    int deleteRights(int userId);

    //查询某个用户的权限
    List<Rights> listRights(int userId);

    //判断用户是否有某某权限
    Rights ifUserHasRights(int userId,int rightsType);
}
