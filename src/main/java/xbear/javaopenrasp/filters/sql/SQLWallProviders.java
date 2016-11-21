package xbear.javaopenrasp.filters.sql;

import com.alibaba.druid.wall.WallProvider;
import com.alibaba.druid.wall.spi.MySqlWallProvider;
import com.alibaba.druid.wall.spi.SQLServerWallProvider;
import xbear.javaopenrasp.util.Console;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xbear on 2016/11/13.
 */
public class SQLWallProviders {

    private static Map<String, WallProvider> providerMap = new ConcurrentHashMap<String, WallProvider>();

    public static WallProvider getProvider(String dbType) {
        switch (dbType.trim()) {
            case "mysql":
                if (!providerMap.containsKey("mysql")) {
                    providerMap.put("mysql", new MySqlWallProvider());
                    setConfig(providerMap.get("mysql"));
                }
                return providerMap.get("mysql");
            case "sqlserver":
                if (!providerMap.containsKey("sqlserver")) {
                    providerMap.put("sqlserver", new SQLServerWallProvider());
                    setConfig(providerMap.get("sqlserver"));
                }
                return providerMap.get("sqlserver");
            default:
                if (!providerMap.containsKey("mysql")) {
                    providerMap.put("mysql", new MySqlWallProvider());
                    setConfig(providerMap.get("mysql"));
                }
                return providerMap.get("mysql");
        }
    }

    private static void setConfig(WallProvider provider) {
        provider.getConfig().setCommentAllow(false);
        //set you want
        Console.log(provider.getBlackList().toString());
    }

}
