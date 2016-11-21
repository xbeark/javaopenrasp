package xbear.javaopenrasp.visitors.rce;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by xbear on 2016/11/13.
 */
public class OgnlVisitor extends ClassVisitor {

    public String className;

    public OgnlVisitor(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("parseExpression".equals(name) && "(Ljava/lang/String;)Ljava/lang/Object;".equals(desc)) {
            mv = new OgnlVisitorAdapter(mv, access, name, desc);
        }
        return mv;
    }

}
