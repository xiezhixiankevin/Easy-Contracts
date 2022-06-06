package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.ContractProcessService;
import bjtu.pt.easycontracts.service.EmailService;
import bjtu.pt.easycontracts.service.UserService;
import bjtu.pt.easycontracts.utils.Global;
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
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Override
    public Map<Integer, List<Contract>> listConTractUserNeedDeal(int userId,List<Boolean> rights) {
        // 获得所有待分配的合同
        List<Contract> contractsList0 = new ArrayList<>();
        ContractExample waitingContracts = new ContractExample();
        waitingContracts.createCriteria().andTypeEqualTo(WAITING);
        contractsList0 = contractMapper.selectByExample(waitingContracts);

        List<Contract> contractsList1=new ArrayList<>();
        List<Contract> contractsList2=new ArrayList<>();
        List<Contract> contractsList3=new ArrayList<>();
        List<Contract> contractsList4=new ArrayList<>();

        List<ContractProcess> contractProcessList;
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

        //查询对应userId具有分配能力的 待分配的合同

        List<Contract> contractsList5=new ArrayList<>();
        //查询处在待分配的contract
        List<Contract> distributedContractsList = new ArrayList<>();
        ContractExample distributedContract=new ContractExample();
        distributedContract.createCriteria().andTypeEqualTo(WAITING);
        distributedContractsList = contractMapper.selectByExample(distributedContract);//所有type为WAITING的合同

        ContractProcessExample contractProcessExample5=new ContractProcessExample();
        List<ContractProcess> distributedContractProcessList;
        int flag5=0;
        for(int i=0;i<distributedContractsList.size();i++){
            contractProcessExample5.createCriteria().andContractidEqualTo(distributedContractsList.get(i).getContractid());
            distributedContractProcessList=contractProcessMapper.selectByExample(contractProcessExample5);// 合同流程表里所有合同为对应合同ID的合同流程
            if(rights.get(0)==true){
                for(int j=0;j<distributedContractProcessList.size();j++){
                    if(distributedContractProcessList.get(j).getType()==COUNTERSIGN){
                        flag5=1;//如果存在 flag就为1 即不需要操作
                    }
                }
            }
            if(flag5==0){
                contractsList5.add(distributedContractsList.get(i));
            }
            flag5=0;
            if(rights.get(1)==true){
                for(int j=0;j<distributedContractProcessList.size();j++){
                    if(distributedContractProcessList.get(j).getType()==EXAM){
                        flag5=1;//如果存在 flag就为1 即不需要操作
                    }
                }
            }
            if(flag5==0){
                contractsList5.add(distributedContractsList.get(i));
            }
            flag5=0;
            if(rights.get(2)==true){
                for(int j=0;j<distributedContractProcessList.size();j++){
                    if(distributedContractProcessList.get(j).getType()==SIGN){
                        flag5=1;//如果存在 flag就为1 即不需要操作
                    }
                }
            }
            if(flag5==0){
                contractsList5.add(distributedContractsList.get(i));
            }
            flag5=0;
        }
        if(!contractsList5.isEmpty())
            listConTractUserNeedDeal.put(5,contractsList5);// key 为5 表示待分配的合同
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
    public int updateProcess(int userId, int contractId, int type, ContractProcess contractProcess) {
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        ContractProcessExample.Criteria criteria = contractProcessExample.createCriteria();

        /* 添加检索条件 */
        criteria.andUseridEqualTo(userId);
        criteria.andContractidEqualTo(contractId);
        criteria.andTypeEqualTo(type);

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
        //分配后检查是否合同已经每一个过程都有人了，是的话修改合同状态为会签,并把所有的会签process的state改成DOING
        if (ifAssignAll(contractId)){
            //
            Contract contract = new Contract();
            contract.setType(COUNTERSIGNING);
            ContractExample contractExample = new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExampleSelective(contract,contractExample);
            //
            ContractProcess contractProcess = new ContractProcess();
            contractProcess.setState(DOING);
            ContractProcessExample contractProcessExample = new ContractProcessExample();
            contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(COUNTERSIGN);
            contractProcessMapper.updateByExampleSelective(contractProcess,contractProcessExample);
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

    @Override
    public void finalize(int contractId, int userId) {
        //修改定稿人的process
        ContractProcess contractProcess = new ContractProcess(contractId, userId, FINALIZE, Global.PASS);
        updateProcess(userId,contractId,Global.FINALIZE,contractProcess);
        //设置所有审批人state
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(Global.EXAM);
        ContractProcess contractProcess1 = new ContractProcess();
        contractProcess1.setState(DOING);
        contractProcessMapper.updateByExampleSelective(contractProcess1,contractProcessExample);
        //修改合同进入审批阶段
        Contract contract = new Contract();
        Contract contract1 = contractMapper.selectByPrimaryKey(contractId);
        contract.setFailuretimes(contract1.getFailuretimes());
        contract.setType(EXAMMING);
        contract.setContractid(contractId);
        contractMapper.updateByPrimaryKeySelective(contract);
    }


    @Override
    public String getCounterSignOpinion(int contractId) {
        //查出所有会签
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(Global.COUNTERSIGN);
        List<ContractProcess> contractProcesses = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);
        //拼接会签意见
        StringBuilder stringBuilder = new StringBuilder();
        for (ContractProcess contractProcess : contractProcesses) {
            String content = contractProcess.getContent();
            String username = userService.getUserById(contractProcess.getUserid()).getUsername();
            stringBuilder.append(username).append(" :").append("\n").append(content).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String getExamOpinion(int contractId) {
        //查出所有审批,只显示未通过
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM);
        List<ContractProcess> contractProcesses = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);
        //拼接审批意见
        StringBuilder stringBuilder = new StringBuilder();
        for (ContractProcess contractProcess : contractProcesses) {
            String content = contractProcess.getContent();
            String username = userService.getUserById(contractProcess.getUserid()).getUsername();
            stringBuilder.append(username).append(" : ").append("\n").append(content).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void sendEmailRemind(Contract contract)
    {
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        contractProcessExample.createCriteria().andTypeEqualTo(NOT_COME).andContractidEqualTo(contract.getContractid());
        List<ContractProcess> contractProcessList=new ArrayList<>();
        contractProcessList=contractProcessMapper.selectByExample(contractProcessExample);

        for(int i=0;i<contractProcessList.size();i++){
            User user = userService.getUserById(contractProcessList.get(i).getUserid());
            if(contractProcessList.get(i).getType()==COUNTERSIGN)
            emailService.sendSimpleMail(user.getEmail() , "Remind the work" , "You have work of countersign contract to be done, remember to finish it!");
            if(contractProcessList.get(i).getType()==FINALIZE)
                emailService.sendSimpleMail(user.getEmail() , "Remind the work" , "You have work of finalize contract to be done, remember to finish it!");
            if(contractProcessList.get(i).getType()==EXAM)
                emailService.sendSimpleMail(user.getEmail() , "Remind the work" , "You have work of exam contract to be done, remember to finish it!");
            if(contractProcessList.get(i).getType()==SIGN)
                emailService.sendSimpleMail(user.getEmail() , "Remind the work" , "You have work of sign contract to be done, remember to finish it!");
        }

    }
}
