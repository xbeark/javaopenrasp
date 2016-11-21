package xbear.javaopenrasp.filters.rce;

import xbear.javaopenrasp.config.Config;
import xbear.javaopenrasp.filters.SecurityFilterI;
import xbear.javaopenrasp.util.Console;
import xbear.javaopenrasp.util.StackTrace;

/**
 * Created by xbear on 2016/11/13.
 */
public class OgnlFilter implements SecurityFilterI {

    public static boolean staticFilter(Object forCheck) {
        String moudleName = "ognl/Ognl";
        String ognlExpression = (String) forCheck;
        Console.log("prepare to parse ognlExpression:" + ognlExpression);
        String mode = (String) Config.moudleMap.get("ognl/Ognl").get("mode");
        switch (mode) {
            case "block":
                Console.log("block" + ognlExpression);
                return false;
            case "white":
                if (Config.isWhite(moudleName, ognlExpression)) {
                    Console.log("parse ognlExpression:" + ognlExpression);
                    return true;
                }
                Console.log("block" + ognlExpression);
                return false;
            case "black":
                if (Config.isBlack(moudleName, ognlExpression)) {
                    Console.log("block parse ognlExpression" + ognlExpression);
                    return false;
                }
                Console.log("exec command:" + ognlExpression);
                return true;
            case "log":
            default:
                Console.log("parse ognlExpression" + ognlExpression);
                Console.log("log stack trace:\r\n" + StackTrace.getStackTrace());
                return true;
        }
    }

    @Override
    public boolean filter(Object forCheck) {
        return true;
    }

}
