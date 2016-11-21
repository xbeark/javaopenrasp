package xbear.javaopenrasp.filters.rce;

import org.apache.commons.lang3.StringUtils;
import xbear.javaopenrasp.config.Config;
import xbear.javaopenrasp.filters.SecurityFilterI;
import xbear.javaopenrasp.util.Console;
import xbear.javaopenrasp.util.StackTrace;

import java.util.List;

/**
 * Created by xbear on 2016/11/13.
 */
public class PrcessBuilderFilter implements SecurityFilterI {

    @Override
    public boolean filter(Object forCheck) {
        String moudleName = "java/lang/ProcessBuilder";
        List<String> commandList = (List<String>) forCheck;
        String command = StringUtils.join(commandList, " ").trim().toLowerCase();
        Console.log("prepare to exec command:" + command);
        String mode = (String) Config.moudleMap.get(moudleName).get("mode");
        switch (mode) {
            case "block":
                Console.log("block" + command);
                return false;
            case "white":
                if (Config.isWhite(moudleName, command)) {
                    Console.log("exec command:" + command);
                    return true;
                }
                Console.log("block" + command);
                return false;
            case "black":
                if (Config.isBlack(moudleName, command)) {
                    Console.log("block command exec" + command);
                    return false;
                }
                Console.log("exec command:" + command);
                return true;
            case "log":
            default:
                Console.log("exc commond" + command);
                Console.log("log stack trace:\r\n" + StackTrace.getStackTrace());
                return true;
        }
    }

}
