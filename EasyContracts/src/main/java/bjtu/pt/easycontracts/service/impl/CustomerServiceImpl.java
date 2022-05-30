package bjtu.pt.easycontracts.service.impl;

import bjtu.pt.easycontracts.mapper.CustomerMapper;
import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.pojo.table.CustomerExample;
import bjtu.pt.easycontracts.service.CustomerService;
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
        if (customer.getAccount() != null)
            criteria.andAccountEqualTo(customer.getAccount());
        if (customer.getEmail() != null)
            criteria.andEmailEqualTo(customer.getEmail());
        if (customer.getCustomerid() != null)
            criteria.andCustomeridEqualTo(customer.getCustomerid());
        if (customer.getAddress() != null)
            criteria.andAddressEqualTo(customer.getAddress());
        if (customer.getBank() != null)
            criteria.andBankEqualTo(customer.getBank());
        if (customer.getCustomername() != null)
            criteria.andCustomernameLike("%"+customer.getCustomername()+"%");
        if (customer.getPhone() != null)
            criteria.andPhoneEqualTo(customer.getPhone());

        return customerMapper.selectByExample(customerExample);
    }
}