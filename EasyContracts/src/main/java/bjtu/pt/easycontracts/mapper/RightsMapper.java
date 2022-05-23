package bjtu.pt.easycontracts.mapper;

import bjtu.pt.easycontracts.pojo.table.Rights;
import bjtu.pt.easycontracts.pojo.table.RightsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RightsMapper {
    int countByExample(RightsExample example);

    int deleteByExample(RightsExample example);

    int deleteByPrimaryKey(Integer rightid);

    int insert(Rights record);

    int insertSelective(Rights record);

    List<Rights> selectByExample(RightsExample example);

    Rights selectByPrimaryKey(Integer rightid);

    int updateByExampleSelective(@Param("record") Rights record, @Param("example") RightsExample example);

    int updateByExample(@Param("record") Rights record, @Param("example") RightsExample example);

    int updateByPrimaryKeySelective(Rights record);

    int updateByPrimaryKey(Rights record);
}