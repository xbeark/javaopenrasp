package xbear.javaopenrasp.visitors.rce;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by xbear on 2016/11/13.
 */
public class ProcessBuilderVisitorAdapter extends AdviceAdapter {
    public ProcessBuilderVisitorAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitTypeInsn(NEW, "xbear/javaopenrasp/filters/rce/PrcessBuilderFilter");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "xbear/javaopenrasp/filters/rce/PrcessBuilderFilter", "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "java/lang/ProcessBuilder", "command", "Ljava/util/List;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "xbear/javaopenrasp/filters/rce/PrcessBuilderFilter", "filter", "(Ljava/lang/Object;)Z", false);

        Label l92 = new Label();
        mv.visitJumpInsn(IFNE, l92);
        mv.visitTypeInsn(NEW, "java/io/IOException");
        mv.visitInsn(DUP);
        mv.visitLdcInsn("invalid character in command because of security");
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/IOException", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l92);

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
    }
}

