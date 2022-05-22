package bjtu.pt.easycontracts.mapper;

import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractProcessMapper {
    int countByExample(ContractProcessExample example);

    int deleteByExample(ContractProcessExample example);

    int deleteByPrimaryKey(Integer useUserid);

    int insert(ContractProcess record);

    int insertSelective(ContractProcess record);

    List<ContractProcess> selectByExampleWithBLOBs(ContractProcessExample example);

    List<ContractProcess> selectByExample(ContractProcessExample example);

    ContractProcess selectByPrimaryKey(Integer useUserid);

    int updateByExampleSelective(@Param("record") ContractProcess record, @Param("example") ContractProcessExample example);

    int updateByExampleWithBLOBs(@Param("record") ContractProcess record, @Param("example") ContractProcessExample example);

    int updateByExample(@Param("record") ContractProcess record, @Param("example") ContractProcessExample example);

    int updateByPrimaryKeySelective(ContractProcess record);

    int updateByPrimaryKeyWithBLOBs(ContractProcess record);

    int updateByPrimaryKey(ContractProcess record);
}