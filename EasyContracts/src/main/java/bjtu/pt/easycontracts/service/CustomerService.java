package bjtu.pt.easycontracts.service;

import bjtu.pt.easycontracts.pojo.table.Customer;

import java.util.List;

/**
 * <Description> Customer
 *
 * @author 26802
 * @version 1.0
 * @ClassName Customer
 * @taskId
 * @see bjtu.pt.easycontracts.service
 */
public interface CustomerService {

    //新增客户(fbf)
    int addCustomer(Customer customer);

    //删除客户(fbf)
    int deleteCustomer(int customerId);

    //修改客户(wj)
    int updateCustomer(int customerId,Customer customer);

    //查询客户(wj)
    List<Customer> listCustomerSelective(Customer customer);

    //分页查询客户xzx
    List<Customer> listCustomerSelectiveByPn(Customer customer,Integer pn);
}
