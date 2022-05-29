package bjtu.pt.easycontracts.pojo.table;


import bjtu.pt.easycontracts.utils.Global;

import java.util.Date;

public class ContractProcess {
    private Integer contractid;

    private Integer userid;

    private Integer type;

    private Integer state = Global.NOT_COME;

    private Date time = new Date();

    private String content;

    public ContractProcess() {
    }

    public ContractProcess(Integer contractid, Integer userid, Integer type, Integer state) {
        this.contractid = contractid;
        this.userid = userid;
        this.type = type;
        this.state = state;
    }

    public ContractProcess(Integer contractid, Integer userid, Integer type) {
        this.contractid = contractid;
        this.userid = userid;
        this.type = type;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}