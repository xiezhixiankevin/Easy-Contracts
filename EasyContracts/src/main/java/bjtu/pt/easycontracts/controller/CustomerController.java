package bjtu.pt.easycontracts.controller;

import bjtu.pt.easycontracts.pojo.table.Customer;
import bjtu.pt.easycontracts.service.CustomerService;
import bjtu.pt.easycontracts.utils.Global;
import bjtu.pt.easycontracts.utils.ReturnObject;
import java.util.*;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <Description> CustomerController
 *
 * @author 26802
 * @version 1.0
 * @ClassName CustomerController
 * @taskId
 * @see bjtu.pt.easycontracts.controller
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/listCustomer/{pn}")
    @ResponseBody
    public ReturnObject<PageInfo> getCustomerListSelective(Customer customer,
                                                           @PathVariable("pn")Integer pn){
        List<Customer> customerList = customerService.listCustomerSelectiveByPn(customer, pn);
        PageInfo pageInfo = new PageInfo(customerList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(Global.SUCCESS,pageInfo);
        return result;
    }

    @PostMapping("/delete/{customerId}")
    @ResponseBody
    public String deleteCustomer(@PathVariable("customerId")Integer customerId){
        customerService.deleteCustomer(customerId);//i返回的是影响的行数，所以=0代表没有这个人，并不表示失败
        return String.valueOf(Global.SUCCESS);
    }

    @PostMapping("/crate")
    @ResponseBody
    public String crateCustomer(Customer customer){
        if(customerService.addCustomer(customer)>0){
            return String.valueOf(Global.SUCCESS);
        }
        else {
            return String.valueOf(Global.FAIL);
        }
    }
}
