package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import bjtu.pt.easycontracts.service.ContractProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;

@Service
public class ContractProcessServiceImpl implements ContractProcessService {
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId) {
        // 获得所有待分配的合同
        List<Contract> contractsList0 = new ArrayList<>();
        ContractExample waitingContracts = new ContractExample();
        waitingContracts.createCriteria().andTypeEqualTo(WAITING);
        contractsList0 = contractMapper.selectByExample(waitingContracts);

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
        // FIXME: 这里的待分配和其他的不同
        if(!contractsList0.isEmpty())
            listConTractUserNeedDeal.put(0, contractsList0);
        // FIXME: 如果要修改以下代码请告知，因为涉及到 me.html
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

    @Override
    public int addUser(Integer contractid, Integer userid, Integer type) {
        java.util.Date date = new java.util.Date();
        ContractProcess contractProcess = new ContractProcess();
        contractProcess.setContent(null);
        contractProcess.setContractid(contractid);
        contractProcess.setState(0);
        contractProcess.setTime(date);
        contractProcess.setUserid(userid);
        contractProcess.setType(type);
        if(contractProcessMapper.insert(contractProcess)>0){
            return SUCCESS;
        }
        else {
            return FAIL;
        }
    }

    @Override
    public int assignUsers(Integer contractId,Map<Integer, List<Integer>> users) {
        //分配会签
        List<Integer> userList = users.get(PERMISSION_COUNTERSIGN_CONTRACT);
        if (!userList.isEmpty()){
            for (Integer userId : userList) {
                ContractProcess contractProcess = new ContractProcess(contractId,userId,COUNTERSIGN);
                insertProcess(contractProcess);
            }
        }
        //分配审批
        userList = users.get(PERMISSION_APPROVE_CONTRACT);
        if (!userList.isEmpty()){
            for (Integer userId : userList) {
                ContractProcess contractProcess = new ContractProcess(contractId,userId,EXAM);
                insertProcess(contractProcess);
            }
        }
        //分配签订
        userList = users.get(PERMISSION_SIGN_CONTRACT);
        if (!userList.isEmpty()){
            for (Integer userId : userList) {
                ContractProcess contractProcess = new ContractProcess(contractId,userId,SIGN);
                insertProcess(contractProcess);
            }
        }
        //分配后检查是否合同已经每一个过程都有人了，是的话修改合同状态为会签
        if (ifAssignAll(contractId)){
            Contract contract = new Contract();
            contract.setType(COUNTERSIGNING);
            ContractExample contractExample = new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExampleSelective(contract,contractExample);
        }
        return SUCCESS;
    }

    @Override
    public boolean ifAssignAll(Integer contractId) {
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);
        List<ContractProcess> contractProcesses = contractProcessMapper.selectByExample(contractProcessExample);
        boolean[] flag = {false,false,false,false};
        for (ContractProcess contractProcess : contractProcesses) {
            if (contractProcess.getType()==COUNTERSIGN){
                flag[COUNTERSIGN-1] = true;
            }
            if (contractProcess.getType()==FINALIZE){
                flag[FINALIZE-1] = true;
            }
            if (contractProcess.getType()==EXAM){
                flag[EXAM-1] = true;
            }
            if (contractProcess.getType()==SIGN){
                flag[SIGN-1] = true;
            }
            int times = 0;
            for (boolean b : flag) {
                if (b){
                    times++;
                }
            }
            if (times == 4){
                return true;
            }
        }
        return false;
    }


}
