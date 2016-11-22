package xbear.javaopenrasp.config;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alibaba.druid.support.json.JSONUtils;

import xbear.javaopenrasp.util.Console;

/**
 * Created by xbear on 2016/11/13.
 */
public class Config {
	
	public static Map<String, Map<String, Object>> moudleMap = new ConcurrentHashMap<String, Map<String, Object>>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean initConfig() {
		String configStr = readConfig("/main.config");
		if (configStr == null) {
			Console.log("init failed because of config file error");
			return false;
		}
		
		Map configMap = (Map) JSONUtils.parse(configStr);
		List<Map> moudleList = (List<Map>) configMap.get("moudle");
		for (Map m: moudleList) {
			Map<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
			tmpMap.put("loadClass", m.get("loadClass"));
			tmpMap.put("mode", m.get("mode"));
			tmpMap.put("whiteList", new CopyOnWriteArrayList<String>((Collection) m.get("whiteList")));
			tmpMap.put("blackList", new CopyOnWriteArrayList<String>((Collection) m.get("blackList")));
			moudleMap.put((String)m.get("moudleName"), tmpMap);
		}
		Console.log(moudleMap.toString());
		
		return true;
	}
	
	public static String readConfig(String filename) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream(filename)));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Console.log(sb.toString());
		return sb.toString();

	}
	
	@SuppressWarnings("unchecked")
	public static boolean isBlack(String moudleName, String testStr) {
		List<String> blackList = (List<String>) moudleMap.get(moudleName).get("blackList");
		for (String black: blackList) {
			if (testStr.trim().toLowerCase().indexOf(black.trim().toLowerCase()) > -1) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isWhite(String moudleName, String testStr) {
		List<String> whiteList = (List<String>) moudleMap.get(moudleName).get("whiteList");
		if (whiteList.size() == 0) {
			return false;
		}
		for (String white: whiteList) {
			if (testStr.trim().equals(white.trim())) {
				return false;
			}
		}
		return true;
	}
	
    public static void main(String[] args) {
    	initConfig();
    	
    }
}
