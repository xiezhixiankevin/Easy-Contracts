package bjtu.pt.easycontracts.utils;

/**
 * <Description> Global
 *
 * 用来定义一些通用的参数比如OK表示成功，就不需要在每个类中重复定义
 * @author 26802
 * @version 1.0
 * @ClassName Global
 * @taskId
 * @see bjtu.pt.easycontracts.utils
 */
public class Global {

    public static final int SUCCESS = 200;
    public static final int FAIL = -1;
    public static final int REGISTER = 1; //注册
    public static final int FIND = 0; //找回
    public static final int OK = 200;
    public static final int CODE_ERROR = 201;
    public static final int NAME_EXIST = 202;
    public static final int ERROR = 203;

    // 权限常量
    public static final int PERMISSION_DRAFT_CONTRACT = 1;
    public static final int PERMISSION_FINALIZE_CONTRACT = 2;
    public static final int PERMISSION_SELECT_CONTRACT = 3;
    public static final int PERMISSION_DELETE_CONTRACT = 4;
    public static final int PERMISSION_COUNTERSIGN_CONTRACT = 5;
    public static final int PERMISSION_APPROVE_CONTRACT = 6;
    public static final int PERMISSION_SIGN_CONTRACT = 7;
    public static final int PERMISSION_ASSIGN_COUNTERSIGN = 8;
    public static final int PERMISSION_ASSIGN_APPROVE = 9;
    public static final int PERMISSION_ASSIGN_SIGN = 10;
    public static final int PERMISSION_PROCESS_INQUIRY = 11;
    public static final int PERMISSION_ADD_USER = 12;
    public static final int PERMISSION_EDIT_USER = 13;
    public static final int PERMISSION_SELECT_USER = 14;
    public static final int PERMISSION_DELETE_USER = 15;
    public static final int PERMISSION_ADD_CUSTOMER = 16;
    public static final int PERMISSION_EDIT_CUSTOMER = 17;
    public static final int PERMISSION_SELECT_CUSTOMER = 18;
    public static final int PERMISSION_DELETE_CUSTOMER = 19;
    public static final int PERMISSION_ASSIGN_PERMISSIONS = 20;

    /* contract表状态定义 */
    public static final int WAITING = 1;// 等待分配
    public static final int COUNTERSIGNING = 2; // 正在会签
    public static final int FINALIZING = 3; // 正在定稿
    public static final int EXAMMING = 4; // 正在审批
    public static final int SIGNING = 5; // 正在签订
    public static final int FINISH = 6; // 完成

    /* contract_process表状态定义 */
    public static final int COUNTERSIGN = 1; // 会签状态
    public static final int FINALIZE = 2; // 定稿状态
    public static final int EXAM = 3; // 审批状态
    public static final int SIGN = 4; // 签订状态

    /* contract_process表对应state的当前阶段定义 */
    public static final int NOT_COME = 0; // 未到此状态
    public static final int DOING = 1; // 正在此状态
    public static final int PASS = 2; // 通过此状态
    public static final int VETO = 3; // 在该状态中被否决

    //文件上传路径
    public static final String FILE_PATH = "F://files";
}
