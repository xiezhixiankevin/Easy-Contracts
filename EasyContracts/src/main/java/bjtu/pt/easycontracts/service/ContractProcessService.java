package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;

import java.util.List;
import java.util.Map;

/**
 * <Description> ContractProcessService
 *
 * @author 26802
 * @version 1.0
 * @ClassName ContractProcessService
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface ContractProcessService {

    //根据用户id查出当前他需要处理的所有合同
    /**
     * @return 返回值说明：Integer表示哪一类操作，比如需要他会签，还是审批
     *
     * */
    Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId);//zxc

    //修改合同流程信息
    int updateProcess(int userId, int contractId, ContractProcess contractProcess);//wj

    //添加合同流程信息
    int insertProcess(ContractProcess contractProcess); //zxc

    // 添加人员
    int addUser(Integer contractid,Integer userid,Integer type);

    //一次性为合同分配多个人员
    int assignUsers(Integer contractId,Map<Integer,List<Integer>> users);//xzx

    //查看某一合同是否已全部分配
    boolean ifAssignAll(Integer contractId);//xzx
}
