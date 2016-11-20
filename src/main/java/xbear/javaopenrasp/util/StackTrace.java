package xbear.javaopenrasp.util;

/**
 * Created by xbear on 2016/11/13.
 */
public class StackTrace {
	
	public static String getStackTrace() {
		
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		StringBuilder retStack = new StringBuilder();
		
		if (stackElements.length >= 3) {
			for (int i = 2; i < stackElements.length; i++) {
				retStack.append(stackElements[i].getClassName() + "@" + stackElements[i].getMethodName() + "\r\n");
			}
		} else {
			for (int i = 0; i < stackElements.length; i++) {
				retStack.append(stackElements[i].getClassName() + "@" + stackElements[i].getMethodName() + "\r\n");
			}
		}
		
		return retStack.toString();
	}
	
	public static String getCaller() {
		
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		
		if (stackElements.length >= 3) {
			return stackElements[2].getClassName() + "@" + stackElements[1].getMethodName();
		} else {
			return stackElements[1].getClassName() + "@" + stackElements[1].getMethodName();
		}
	}
	
}
