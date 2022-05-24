package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import bjtu.pt.easycontracts.service.ContractProcessService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ContractProcessServiceImpl implements ContractProcessService
{
    @Autowired
    private ContractProcessMapper contractProcessMapper;

    @Override
    public Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId) {
        return null;
    }

    @Override
    public int updateProcess(int userId, int contractId, ContractProcess contractProcess)
    {
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        ContractProcessExample.Criteria criteria = contractProcessExample.createCriteria();

        /* 添加检索条件 */
        criteria.andUseridEqualTo(userId);
        criteria.andContractidEqualTo(contractId);

        /* 更新内容 */
        contractProcessMapper.updateByExampleSelective(contractProcess , contractProcessExample);

        /* 返回当前合同所处状态 */
        return contractProcess.getType();
    }

    @Override
    public int insertProcess(ContractProcess contractProcess) {
        return 0;
    }
}
