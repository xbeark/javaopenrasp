package xbear.javaopenrasp.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		String configStr = readConfig("main.config");
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
		FileReader file;
		try {
			file = new FileReader(filename);
		} catch (FileNotFoundException e) {
			Console.log("Can't read config");
			e.printStackTrace();
			return null;
		}
				
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(file);
		StringBuilder fileLines = new StringBuilder("");
		String tmpLine = null;
		do {
			try {
				tmpLine = in.readLine();
			} catch (IOException e) {
				Console.log("read config line error");
				e.printStackTrace();
				return null;
			}
			if (tmpLine != null) {
				tmpLine = tmpLine.trim();
				fileLines.append(tmpLine);
			}
		} while (tmpLine != null);
		
		if (fileLines.equals("")) {
			return null;
		}
		
		return fileLines.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isBlack(String moudleName, String testStr) {
		List<String> blackList = (List<String>) moudleMap.get(moudleName).get("blackList");
		for (String black: blackList) {
			if (testStr.trim().contains(black.trim().toLowerCase())) {
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
			if (testStr.trim().equals(white.trim().toLowerCase())) {
				return false;
			}
		}
		return true;
	}
	
    public static void main(String[] args) {
    	initConfig();
    	
    }
}
