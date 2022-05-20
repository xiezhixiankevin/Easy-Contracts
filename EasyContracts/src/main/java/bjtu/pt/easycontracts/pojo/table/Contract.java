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
}