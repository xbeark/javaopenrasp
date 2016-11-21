package xbear.javaopenrasp.visitors.rce;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by xbear on 2016/11/13.
 */
public class OgnlVisitorAdapter extends AdviceAdapter {
    public OgnlVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodEnter() {

        Label l30 = new Label();
        mv.visitLabel(l30);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "xbear/javaopenrasp/filters/rce/OgnlFilter", "staticFilter", "(Ljava/lang/Object;)Z", false);
        Label l31 = new Label();
        mv.visitJumpInsn(IFNE, l31);
        Label l32 = new Label();
        mv.visitLabel(l32);
        mv.visitTypeInsn(NEW, "ognl/OgnlException");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("invalid class in ognl expression because of security");
        mv.visitMethodInsn(INVOKESPECIAL, "ognl/OgnlException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l31);

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}

