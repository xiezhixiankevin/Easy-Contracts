package bjtu.pt.easycontracts.mapper;

import bjtu.pt.easycontracts.pojo.table.RoleRight;
import bjtu.pt.easycontracts.pojo.table.RoleRightExample;
import bjtu.pt.easycontracts.pojo.table.RoleRightKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRightMapper {
    int countByExample(RoleRightExample example);

    int deleteByExample(RoleRightExample example);

    int deleteByPrimaryKey(RoleRightKey key);

    int insert(RoleRight record);

    int insertSelective(RoleRight record);

    List<RoleRight> selectByExample(RoleRightExample example);

    RoleRight selectByPrimaryKey(RoleRightKey key);

    int updateByExampleSelective(@Param("record") RoleRight record, @Param("example") RoleRightExample example);

    int updateByExample(@Param("record") RoleRight record, @Param("example") RoleRightExample example);

    int updateByPrimaryKeySelective(RoleRight record);

    int updateByPrimaryKey(RoleRight record);
}