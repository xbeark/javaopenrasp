package xbear.javaopenrasp.filters.sql;

import com.alibaba.druid.wall.WallCheckResult;
import com.alibaba.druid.wall.WallProvider;
import xbear.javaopenrasp.config.Config;
import xbear.javaopenrasp.filters.SecurityFilterI;
import xbear.javaopenrasp.util.Console;
import xbear.javaopenrasp.util.StackTrace;

/**
 * Created by xbear on 2016/11/13.
 */
public class SQLServerFilter implements SecurityFilterI {
    @Override
    public boolean filter(Object forCheck) {
        String sql = ((String) forCheck).trim();
        WallProvider provider = SQLWallProviders.getProvider("mysql");

        Console.log("prepare to exec sql:" + sql);
        String mode = (String) Config.moudleMap.get("com/microsoft/jdbc/base/BaseStatement").get("mode");

        switch (mode) {
            case "check":
                Console.log("check: " + sql);
                WallCheckResult result = provider.check(sql);
                if (result.getViolations().size() > 0) {
                    for (int i = 0; i < result.getViolations().size(); i++) {
                        Console.log("Warning: " + result.getViolations().get(i).getMessage());
                    }
                    return false;
                }
                return true;
            case "log":
            default:
                Console.log("exc sql: " + sql);
                Console.log("log stack trace:\r\n" + StackTrace.getStackTrace());
                return true;
        }

    }
}
