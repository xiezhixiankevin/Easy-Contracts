package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.ContractMapper;
import bjtu.pt.easycontracts.pojo.table.Contract;
import bjtu.pt.easycontracts.pojo.table.ContractExample;
import bjtu.pt.easycontracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService
{
    @Autowired
    private ContractMapper contractMapper;

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
        ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractidEqualTo(id);
        return contractMapper.deleteByExample(contractExample);
    }

    @Override
    public int updateContract(int id, Contract newContract) {
        return 0;
    }

    @Override
    public int countersignContract(int contractId, int userId, String opinion) {
        return 0;
    }

    @Override
    public int examineConTract(int contractId, int userId, String opinion, boolean ifPass) {
        return 0;
    }

    @Override
    public int signContract(int contractId, int userId, String opinion) {
        return 0;
    }
}
