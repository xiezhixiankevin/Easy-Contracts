package bjtu.pt.easycontracts.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <Description> TimeUtil
 *
 * @author 26802
 * @version 1.0
 * @ClassName TimeUtil
 * @taskId
 * @see bjtu.pt.easycontracts.utils
 */
public class TimeUtil {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date strToDate(String str){
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            System.out.println("转换时发生异常，可能是格式不正确");
            return null;
        }
    }

}
