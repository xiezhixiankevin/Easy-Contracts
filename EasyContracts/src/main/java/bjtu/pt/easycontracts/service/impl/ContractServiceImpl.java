package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.mapper.ContractProcessMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.pojo.table.ContractProcess;
import bjtu.pt.easycontracts.pojo.table.ContractProcessExample;
import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import bjtu.pt.easycontracts.utils.Global;

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
        return 0;
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
        return 0;
    }

    @Override
    public int countersignContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0;
        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractprocess表的检索条件 */
        contractProcessExample.createCriteria().andUseridEqualTo(userId);
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);

        /* 修改contract记录状态为正在定稿状态 */
        contract.setType(3);
        updateLine += contractMapper.updateByPrimaryKey(contract);

        /* 遍历修改contract记录,正在会签状态的改为通过会签状态并将会签内容输入进去，未到定稿状态的改为正在定稿状态 */
        for (ContractProcess contractProcess : contractProcessList)
        {
            ContractProcessExample contractProcessExample1 = new ContractProcessExample();// 专门用来更新一条相应记录
            ContractProcessExample.Criteria criteria = contractProcessExample1.createCriteria();

            /* 更新检索条件 */
            criteria.andContractidEqualTo(contractProcess.getContractid());
            criteria.andUseridEqualTo(contractProcess.getUserid());
            criteria.andTypeEqualTo(contractProcess.getType());

            /* 如果是合同对应会签阶段的记录，则修改状态并写入内容 */
            if (contractProcess.getType() == Global.COUNTERSIGN)
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                contractProcess.setState(Global.PASS);
                contractProcess.setContent(opinion);
                contractProcess.setTime(df.format(System.currentTimeMillis()));
            }

            /* 如果合同对应定稿阶段的记录，则修改状态 */
            if (contractProcess.getType() == Global.FINALIZE)
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                contractProcess.setState(Global.DOING);
                contractProcess.setTime(df.format(System.currentTimeMillis()));
            }

            /* 更新数据库对应记录内容 */
            updateLine += contractProcessMapper.updateByExample(contractProcess , contractProcessExample1);
        }

        return updateLine;
    }

    @Override
    public int examineConTract(int contractId, int userId, String opinion, boolean ifPass) {
        return 0;
    }

    @Override
    public int signContract(int contractId, int userId, String opinion)
    {
        int updateLine = 0;
        ContractExample contractExample = new ContractExample();
        ContractProcessExample contractProcessExample = new ContractProcessExample();

        /* 添加contract表的检索条件 */
        contractExample.createCriteria().andContractidEqualTo(contractId);

        /* 添加contractprocess表的检索条件 */
        contractProcessExample.createCriteria().andUseridEqualTo(userId);
        contractProcessExample.createCriteria().andContractidEqualTo(contractId);

        /* 找到对应的记录 */
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        List<ContractProcess> contractProcessList = contractProcessMapper.selectByExample(contractProcessExample);

        /* 修改contract记录状态修改为完成状态 */
        contract.setType(Global.FINISH);
        updateLine += contractMapper.updateByPrimaryKey(contract);

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
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                contractProcess.setState(Global.PASS);
                contractProcess.setContent(opinion);
                contractProcess.setTime(df.format(System.currentTimeMillis()));
            }

            /* 更新数据库对应记录内容 */
            updateLine += contractProcessMapper.updateByExample(contractProcess , contractProcessExample1);
        }

        return updateLine;
    }
}
