package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.*;
import bjtu.pt.easycontracts.service.ContractProcessService;
import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SimpleTimeZone;

import static bjtu.pt.easycontracts.utils.Global.*;

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
            criteria.andContractnameLike(contract.getContractname());
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
        return SUCCESS;
    }

    @Override
    public int deleteContract(int id)
    {
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractidEqualTo(id);
        return contractMapper.deleteByExample(contractExample);
    }

    @Override
    public int updateContract(int id, Contract newContract) {
        deleteContract(id);
        addContract(newContract);
        return SUCCESS;
    }

    @Override
    public int countersignContract(int contractId, int userId, String opinion) {
        return 0;
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
    public int signContract(int contractId, int userId, String opinion) {
        return 0;
    }
}
