package xbear.javaopenrasp.visitors.sql;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by xbear on 2016/11/13.
 */
public class MySQLVisitorAdapter extends AdviceAdapter {
    public MySQLVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Already in sql");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitTypeInsn(NEW, "xbear/javaopenrasp/filters/sql/MySQLFilter");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "xbear/javaopenrasp/filters/sql/MySQLFilter", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 3);
        mv.visitVarInsn(ALOAD, 3);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "xbear/javaopenrasp/filters/sql/MySQLFilter", "filter", "(Ljava/lang/Object;)Z", false);

        Label l92 = new Label();
        mv.visitJumpInsn(IFNE, l92);
        mv.visitTypeInsn(NEW, "java/sql/SQLException");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("invalid sql because of security");
        mv.visitMethodInsn(INVOKESPECIAL, "java/sql/SQLException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l92);

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}
