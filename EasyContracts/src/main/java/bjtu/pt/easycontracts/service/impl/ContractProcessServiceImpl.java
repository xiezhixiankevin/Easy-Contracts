package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import bjtu.pt.easycontracts.service.ContractProcessService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;

public class ContractProcessServiceImpl implements ContractProcessService
{
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId) {
        List<Contract> contractsList1=new ArrayList<>();
        List<Contract> contractsList2=new ArrayList<>();
        List<Contract> contractsList3=new ArrayList<>();
        List<Contract> contractsList4=new ArrayList<>();

        List<ContractProcess> contractProcessList=new ArrayList<>();
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        contractProcessExample.createCriteria().andUseridEqualTo(userId).andStateEqualTo(DOING);
        contractProcessList=contractProcessMapper.selectByExample(contractProcessExample);

        ContractExample contractExample=new ContractExample();
        for(int i=0;i<contractProcessList.size();i++){
            Contract contract=contractMapper.selectByPrimaryKey(contractProcessList.get(i).getContractid());
            if(contract.getType()==COUNTERSIGNING)
                contractsList1.add(contract);
            if(contract.getType()==FINALIZING)
                contractsList2.add(contract);
            if(contract.getType()==EXAMMING)
                contractsList3.add(contract);
            if(contract.getType()==SIGNING)
                contractsList4.add(contract);

        }

        Map<Integer, List<Contract>> listConTractUserNeedDeal=new HashMap<Integer, List<Contract>>();
        if(!contractsList1.isEmpty())
            listConTractUserNeedDeal.put(COUNTERSIGN,contractsList1);
        if(!contractsList2.isEmpty())
            listConTractUserNeedDeal.put(FINALIZE,contractsList2);
        if(!contractsList3.isEmpty())
            listConTractUserNeedDeal.put(EXAM,contractsList3);
        if(!contractsList4.isEmpty())
            listConTractUserNeedDeal.put(SIGN,contractsList4);
        return listConTractUserNeedDeal;
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
        contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample);

        /* 返回当前合同所处状态 */
        return contractProcess.getType();
    }

    @Override
    public int insertProcess(ContractProcess contractProcess) {
        contractProcessMapper.insert(contractProcess);
        return SUCCESS;
    }
}
