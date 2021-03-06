package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.ContractAttachment;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

/**
 * <Description> ContractFileService
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractFileService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface ContractFileService {

    //添加附件(不仅要添加到数据库中，还要保存到服务器)
    int addContractFile(ContractAttachment contractAttachment, MultipartFile upload);

    //删除附件(从数据库和服务器从都删除)
    int deleteContractFile(ContractAttachment contractAttachment);


    int deleteFile(String filePath);

    ContractAttachment getContractAttachment(int contractId,String fileName);

    List<ContractAttachment> getContractFileListOfContract(int contractId);
}
