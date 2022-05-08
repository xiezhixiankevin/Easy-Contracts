package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Role;
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
public interface RoleService {

    //分配角色(考虑到前端分配和修改时是选择框一次性提交，而不是一个个删或者改，所以每次分配之前记得把这个人的所有已有的角色删完再分配)
    int allocationRoles(int userId, List<Role> roles);

    //分配一个角色
    int allocationRole(int userId,Role role);

    //删除角色
    int deleteRole(int userId);

    //查询某个用户的角色
    List<Role> listRoles(int userId);

    //判断用户是否有某某角色
    Role ifUserHasRole(int userId,int roleType);
}
