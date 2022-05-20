package bjtu.pt.easycontracts.mapper;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractMapper {
    int countByExample(ContractExample example);

    int deleteByExample(ContractExample example);

    int deleteByPrimaryKey(Integer contractid);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExampleWithBLOBs(ContractExample example);

    List<Contract> selectByExample(ContractExample example);

    Contract selectByPrimaryKey(Integer contractid);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExampleWithBLOBs(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKeyWithBLOBs(Contract record);

    int updateByPrimaryKey(Contract record);
}