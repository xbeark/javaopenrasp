package xbear.javaopenrasp;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import xbear.javaopenrasp.config.Config;
import xbear.javaopenrasp.util.Console;

/**
 * Created by xbear on 2016/11/13.
 */
public class Agent {
	
	public static void premain(String agentArgs, Instrumentation inst) 
        throws ClassNotFoundException, UnmodifiableClassException { 
		Console.log("init");
		init();
		inst.addTransformer(new ClassTransformer()); 
	}	
	
	private static boolean init() {
		Config.initConfig();
		return true;
	}
} 
