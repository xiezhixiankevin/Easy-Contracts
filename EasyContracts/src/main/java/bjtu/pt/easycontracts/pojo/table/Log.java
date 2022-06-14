package bjtu.pt.easycontracts.pojo.table;

import java.util.Date;

public class Log {
    private Integer userid;

    private Integer contractid;

    private Date time = new Date();

    private Integer operatetype;

    private String content;

    //非数据库属性
    private String username;
    private String operatetypeStr;
    private String timeStr;

    public Log() {
    }

    public Log(Integer userid, Integer contractid, Integer operatetype, String content) {
        this.userid = userid;
        this.contractid = contractid;
        this.operatetype = operatetype;
        this.content = content;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(Integer operatetype) {
        this.operatetype = operatetype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperatetypeStr() {
        return operatetypeStr;
    }

    public void setOperatetypeStr(String operatetypeStr) {
        this.operatetypeStr = operatetypeStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}