package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;

import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static bjtu.pt.easycontracts.utils.Global.*;
import java.util.Date;
import java.util.Map;

import bjtu.pt.easycontracts.utils.Global;

@Service
public class ContractServiceImpl implements ContractService
{
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private ContractProcessMapper contractProcessMapper;

    @Override
    public List<Contract> listContractSelective(Contract contract)
    {
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        List<Contract> contractList;

        /* 添加查询条件 */
        if (contract.getCustomerid() != null)
            criteria.andContractidEqualTo(contract.getCustomerid());
        if (contract.getContractname() != null)
            criteria.andContractnameLike('%' + contract.getContractname() + '%');
        if (contract.getContractid() != null)
            criteria.andContractidEqualTo(contract.getContractid());
        if (contract.getBegintime() != null)
            criteria.andBegintimeEqualTo(contract.getBegintime());
        if (contract.getEndtime() != null)
            criteria.andEndtimeEqualTo(contract.getEndtime());
        if (contract.getDrafterid() != null)
            criteria.andDrafteridEqualTo(contract.getDrafterid());
        if (contract.getType() != null)
            criteria.andTypeEqualTo(contract.getType());
        if (contract.getFailuretimes() != null)
            criteria.andFailuretimesEqualTo(contract.getFailuretimes());

        contractList = contractMapper.selectByExample(contractExample);

        return contractList;

    }

    @Override
    public int addContract(Contract contract) {
        contractMapper.insert(contract);
        return contract.getContractid();
    }

    @Override
    public int deleteContract(int id)
    {
        int deleteLine = 0; // 记录删除的行数
        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();
        contractExample.createCriteria().andContractidEqualTo(id);
        contractProcessExample.createCriteria().andContractidEqualTo(id);
        deleteLine += contractMapper.deleteByExample(contractExample);
        deleteLine += contractProcessMapper.deleteByExample(contractProcessExample);

        return deleteLine;
    }

    @Override
    public int updateContract(int id, Contract newContract) {
        deleteContract(id);
        addContract(newContract);
        return SUCCESS;
    }

    @Override
    public int countersignContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0;
        int notFinishedCount = 0; // 判断还有多少会签人员没有完成会签操作

        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractprocess表的检索条件 */
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);

        /* 遍历修改contractProcess记录,正在会签状态的改为通过会签状态并将会签内容输入进去 */
        for (ContractProcess contractProcess : contractProcessList)
        {
            ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
            ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

            /* 更新检索条件 */
            criteria.andContractidEqualTo(contractProcess.getContractid());
            criteria.andUseridEqualTo(contractProcess.getUserid());
            criteria.andTypeEqualTo(contractProcess.getType());

            /* 如果是合同对应会签阶段的记录，如果是对应的会签人员则修改状态并写入内容，如果是别的会签人员则看是否已完成会签内容 */
            if (contractProcess.getType() == Global.COUNTERSIGN)
            {
                if (contractProcess.getUserid() != userId)
                {
                    if (contractProcess.getContent() == null)
                        notFinishedCount++;
                }
                else
                {
                    Date date = new Date();
                    contractProcess.setState(Global.PASS);
                    contractProcess.setContent(opinion);
                    contractProcess.setTime(date);
                }
            }

            /* 更新数据库对应记录内容 */
            updateLine += contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample1);
        }

        /* 判断是否所有会签人员都已完成会签，如果都完成，则再次遍历，找到定稿阶段的记录，更改状态 */
        if (notFinishedCount == 0)
        {
            for (ContractProcess contractProcess : contractProcessList)
            {
                ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
                ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

                /* 更新检索条件 */
                criteria.andContractidEqualTo(contractProcess.getContractid());
                criteria.andUseridEqualTo(contractProcess.getUserid());
                criteria.andTypeEqualTo(contractProcess.getType());

                if (contractProcess.getType() == Global.FINALIZE)
                {
                    contractProcess.setState(Global.DOING);
                    updateLine += contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample1);
                }
            }

            /* 修改contract记录状态为正在定稿状态 */
            contract.setType(3);
            updateLine += contractMapper.updateByPrimaryKey(contract);
        }

        return updateLine;
    }

    @Override
    public int examineConTract(int contractId, int userId, String opinion, boolean ifPass) {
        ContractProcessServiceImpl contractProcessServiceImpl=new ContractProcessServiceImpl();
        ContractProcess contractProcess=new ContractProcess();

        //Date
        java.util.Date date=new java.util.Date();
        contractProcess.setTime(date);

        contractProcess.setContractid(contractId);
        contractProcess.setContent(opinion);
        contractProcess.setUserid(userId);
        if(ifPass==true){
            contractProcess.setState(PASS);
        }else{
            contractProcess.setState(VETO);
        }
        contractProcessServiceImpl.updateProcess(userId,contractId,contractProcess);

        //检查
        ContractProcessExample contractProcessExample=new ContractProcessExample();
        contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM);
        List <ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);
        int flag1=0;//记录操作次数
        int flag2=0;//记录PASS次数
        for(int i=0;i<contractProcessList.size();i++){
            if(contractProcessList.get(i).getState()==PASS  ||contractProcessList.get(i).getState()== VETO)
            {
                flag1++;
                if(contractProcessList.get(i).getState()==PASS){
                    flag2++;
                }
            }
        }
        if(flag2==contractProcessList.size()){
            Contract contract=contractMapper.selectByPrimaryKey(contractId);
            contract.setType(SIGNING);//进入下一阶段签订
            ContractExample contractExample=new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExample(contract,contractExample);
            return SUCCESS;
        }else if(flag1==contractProcessList.size()){
            //将合同进程表里数据 status改成0
            ContractProcessExample contractProcessExample2=new ContractProcessExample();
            contractProcessExample.createCriteria().andContractidEqualTo(contractId).andTypeEqualTo(EXAM);
            List <ContractProcess> contractProcessList2 = contractProcessMapper.selectByExample(contractProcessExample2);
            for(int k=0;k<contractProcessList2.size();k++){
                contractProcessList2.get(k).setState(NOT_COME);
                contractProcessServiceImpl.updateProcess(userId,contractId,contractProcessList2.get(k));
            }
            //
            Contract contract=contractMapper.selectByPrimaryKey(contractId);
            contract.setType(FINALIZING);//重新回到定稿阶段
            contract.setFailuretimes(contract.getFailuretimes()+1);
            ContractExample contractExample=new ContractExample();
            contractExample.createCriteria().andContractidEqualTo(contractId);
            contractMapper.updateByExample(contract,contractExample);
            return SUCCESS;
        }
        return SUCCESS;
    }

    @Override
    public int signContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0;
        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractProcess表的检索条件 */
        contractProcessExample.createCriteria().andUseridEqualTo(userId);
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExampleWithBLOBs(contractProcessExample);

        /* 修改contract记录状态修改为完成状态 */
        contract.setType(Global.FINISH);
        updateLine += contractMapper.updateByPrimaryKeyWithBLOBs(contract);

        /* 遍历修改contract记录,正在签订状态的改为通过签订状态并将签订内容输入进去 */
        for (ContractProcess contractProcess : contractProcessList)
        {
            ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
            ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

            /* 更新检索条件 */
            criteria.andContractidEqualTo(contractProcess.getContractid());
            criteria.andUseridEqualTo(contractProcess.getUserid());
            criteria.andTypeEqualTo(contractProcess.getType());

            /* 如果是合同对应签订阶段的记录，则修改状态并写入内容 */
            if (contractProcess.getType() == Global.COUNTERSIGN)
            {
                Date date = new Date();
                contractProcess.setState(Global.PASS);
                contractProcess.setContent(opinion);
                contractProcess.setTime(date);

                /* 更新数据库对应记录内容 */
                updateLine += contractProcessMapper.updateByExampleWithBLOBs(contractProcess , contractProcessExample1);
            }
        }

        return updateLine;
    }

    @Override
    public Map<Integer, Boolean> getNeedAllocationOfContract(int contractId) {
        return null;
    }

    @Override
    public List<Contract> getNeedAllocationContracts() {
        return null;
    }
}
