package bjtu.pt.easycontracts.pojo.table;

import java.util.Date;

public class Contract {
    private Integer contractid;

    private String contractname;

    private Integer customerid;

    private Date begintime;

    private Date endtime;

    private Integer drafterid;

    private Integer type;

    private Integer failuretimes;

    private String content;

    //非数据库属性
    private String beginTimeStr;
    private String endTimeStr;
    private String customerName;
    private boolean ifBeginFirst = false;
    private boolean ifEndFirst = false;
    private Integer onlyWhich = 0;

    private boolean[] needAllocationProcess = {true,false,true,true};//数组的0，1，2，3分别代表会签，定稿，审批，签订

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getContractname() {
        return contractname;
    }

    public void setContractname(String contractname) {
        this.contractname = contractname == null ? null : contractname.trim();
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getDrafterid() {
        return drafterid;
    }

    public void setDrafterid(Integer drafterid) {
        this.drafterid = drafterid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFailuretimes() {
        return failuretimes;
    }

    public void setFailuretimes(Integer failuretimes) {
        this.failuretimes = failuretimes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public boolean[] getNeedAllocationProcess() {
        return needAllocationProcess;
    }

    public void setNeedAllocationProcess(int index,boolean ifNeed) {
        needAllocationProcess[index] = ifNeed;
    }

    public boolean isIfEndFirst() {
        return ifEndFirst;
    }

    public void setIfEndFirst(boolean ifEndFirst) {
        this.ifEndFirst = ifEndFirst;
    }

    public boolean isIfBeginFirst() {
        return ifBeginFirst;
    }

    public void setIfBeginFirst(boolean ifBeginFirst) {
        this.ifBeginFirst = ifBeginFirst;
    }

    public Integer getOnlyWhich() {
        return onlyWhich;
    }

    public void setOnlyWhich(Integer onlyWhich) {
        this.onlyWhich = onlyWhich;
    }
}