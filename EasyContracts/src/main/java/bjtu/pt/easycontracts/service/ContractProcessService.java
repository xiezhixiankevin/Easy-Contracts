package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Contract;

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

}
