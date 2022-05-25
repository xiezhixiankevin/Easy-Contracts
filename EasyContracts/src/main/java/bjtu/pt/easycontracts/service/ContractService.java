package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Contract;

import java.util.List;
import java.util.Map;

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
    List<Contract> listContractSelective(Contract contract);//wj

    //添加合同，其实就是起草
    int addContract(Contract contract);//zxc

    //删除合同 process表里对应的也要删除
    int deleteContract(int id);//wj

    //修改合同
    int updateContract(int id,Contract newContract);//zxc

    //给合同添加会签意见(每次添加会签以及后记得判断一下是不是所有会签人均已会签，是的话修改合同状态)
    int countersignContract(int contractId ,int userId ,String opinion);//wj

    //给合同添加审批意见，并且记得修改被打回次数并更改状态为定稿，当然一次审批即通过则无需修改被打回次数并进入下一状态
    int examineConTract(int contractId ,int userId , String opinion , boolean ifPass);//zxc

    //签订合同
    int signContract(int contractId ,int userId ,String opinion);//wj

    //获取到当前合同还需要分配哪些过程(会签，定稿...) //zxc
    Map<Integer,Boolean> getNeedAllocationOfContract(int contractId);

    //返回当前数据库中所有等待分配的合同以及每个合同还需分配哪些权限 //fbf
    List<Contract> getNeedAllocationContracts();
}
