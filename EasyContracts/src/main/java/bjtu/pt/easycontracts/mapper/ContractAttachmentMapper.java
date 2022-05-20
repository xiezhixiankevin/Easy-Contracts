package bjtu.pt.easycontracts.mapper;

import bjtu.pt.easycontracts.pojo.table.ContractAttachment;
import bjtu.pt.easycontracts.pojo.table.ContractAttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContractAttachmentMapper {
    int countByExample(ContractAttachmentExample example);

    int deleteByExample(ContractAttachmentExample example);

    int insert(ContractAttachment record);

    int insertSelective(ContractAttachment record);

    List<ContractAttachment> selectByExample(ContractAttachmentExample example);

    int updateByExampleSelective(@Param("record") ContractAttachment record, @Param("example") ContractAttachmentExample example);

    int updateByExample(@Param("record") ContractAttachment record, @Param("example") ContractAttachmentExample example);
}