package xbear.javaopenrasp.transformers;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import xbear.javaopenrasp.visitors.rce.ProcessBuilderVisitor;

/**
 * Created by xbear on 2016/11/13.
 */
public class ProcessBuilderTransformer implements ClassFileTransformer {

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] transformeredByteCode = classfileBuffer;
//		if ("TransClass/TimeTest".equals(className)) {
		if ("java/lang/ProcessBuilder".equals(className)) {
			transformeredByteCode = weavePatch(classfileBuffer, className);
		}
 		return transformeredByteCode;
	}
	
	@SuppressWarnings("finally")
	private byte[] weavePatch(byte[] classfileBuffer, String className) {
		byte[] transformedByteCode = null;
		try {
			ClassReader reader = new ClassReader(classfileBuffer);
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
			ClassVisitor visitor = new ProcessBuilderVisitor(writer, className);
			reader.accept(visitor, ClassReader.EXPAND_FRAMES);
			transformedByteCode = writer.toByteArray();
		} catch (Throwable t) {
			System.out.println("Problem instrumenting java.io.ObjectInputStream -- no deserialization protection in place");
			t.printStackTrace();
		} finally {
			return transformedByteCode;
		}
	}
}
