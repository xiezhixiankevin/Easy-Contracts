package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.Contract;

import java.util.List;

/**
 * <Description> ContractService
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface ContractService {

    /*
    条件性查询合同
    如果想条件查询，传入一个contract对象，比如你想查名称带有北交大的，就设置contract的名称属性为北交大，再传入
    如果想查询全部的合同，传入一个null即可
    */
    List<Contract> listContractSelective(Contract contract);


}
