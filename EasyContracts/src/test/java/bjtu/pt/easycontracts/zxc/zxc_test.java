package bjtu.pt.easycontracts.zxc;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bjtu.pt.easycontracts.utils.Global.*;
import static bjtu.pt.easycontracts.utils.Global.SIGN;

/**
 * <Description> MyTest
 *
 * @author 26802
 * @version 1.0
 * @ClassName MyTest
 * @taskId
 * @see bjtu.pt.easycontracts.xzx
 */
public class zxc_test {
    @Autowired
    private ContractProcessMapper contractProcessMapper;
    @Autowired
    private ContractMapper contractMapper;
    @Test
    public void test(){


        int userId=1;
        List<Boolean> rights=new ArrayList<>();
        rights.add(true);rights.add(true);rights.add(true);

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
        //查询处在待分配的contract
        List<Contract> contractsList5=new ArrayList<>();

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
                    if(distributedContractProcessList.get(j).getType()!=EXAM){
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
                    if(distributedContractProcessList.get(j).getType()!=SIGN){
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
            listConTractUserNeedDeal.put(5,contractsList4);// key 为5 表示待分配的合同
    }

}