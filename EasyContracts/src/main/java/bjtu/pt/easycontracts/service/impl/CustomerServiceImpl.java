package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.pojo.table.CustomerExample;
import bjtu.pt.easycontracts.service.CustomerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int addCustomer(Customer customer) {
        return customerMapper.insert(customer);
    }

    @Override
    public int deleteCustomer(int customerId) {
        return customerMapper.deleteByPrimaryKey(customerId);
    }

    @Override
    public int updateCustomer(int customerId, Customer customer) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andCustomeridEqualTo(customerId);
        customer.setCustomerid(customerId);
        return customerMapper.updateByExample(customer, customerExample);
    }

    @Override
    public List<Customer> listCustomerSelective(Customer customer) {
        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        if(customer==null){
            return customerMapper.selectByExample(null);
        }
        if (customer.getRemark()!=null){
            criteria.andRemarkLike("%"+customer.getAddress()+"%");
        }
        if (customer.getAddress() != null)
            criteria.andAddressLike("%"+customer.getAddress()+"%");
        if (customer.getBank() != null)
            criteria.andBankLike("%"+customer.getBank()+"%");
        if (customer.getCustomername() != null)
            criteria.andCustomernameLike("%"+customer.getCustomername()+"%");


        return customerMapper.selectByExample(customerExample);
    }

    @Override
    public List<Customer> listCustomerSelectiveByPn(Customer customer, Integer pn) {

        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        if(customer==null){
            return customerMapper.selectByExample(null);
        }
        if (customer.getRemark()!=null && customer.getRemark().length()!=0 ){
            criteria.andRemarkLike("%"+customer.getAddress()+"%");
        }
        if (customer.getAddress() != null && customer.getAddress().length()!=0)
            criteria.andAddressLike("%"+customer.getAddress()+"%");
        if (customer.getBank() != null && customer.getBank().length()!=0)
            criteria.andBankLike("%"+customer.getBank()+"%");
        if (customer.getCustomername() != null && customer.getCustomername().length()!=0)
            criteria.andCustomernameLike("%"+customer.getCustomername()+"%");

        PageHelper.startPage(pn,5); //每页显示5个数据
        return customerMapper.selectByExample(customerExample);

    }

    @Override
    public Customer selectById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

}