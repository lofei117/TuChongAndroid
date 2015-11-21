package info.lofei.app.tuchong.utils;

/**
 * Created by jerrysher jerry@jerryzhang.net
 * on 11/21/15.
 */
public class NumberUtil {


    public static int toInt(Object beInt){
        return toInt(beInt, 0);
    }

    public static int toInt(Object beInt, int defaultValue){
        if(beInt == null){
            return defaultValue;
        }
        int returnValue;
        try {
            returnValue = Integer.valueOf(beInt.toString());
        }catch (NumberFormatException e){
            returnValue = defaultValue;
        }
        return returnValue;
    }
}
