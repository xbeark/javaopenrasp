package xbear.javaopenrasp.util;

import org.objectweb.asm.ClassVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xbear on 2016/11/13.
 */
public class Reflections {

    public static Field getField(final Class<?> clazz, final String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        if (field != null)
            field.setAccessible(true);
        else if (clazz.getSuperclass() != null)
            field = getField(clazz.getSuperclass(), fieldName);
        return field;
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        return ctor;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createVisitorIns(final String name, ClassVisitor cv, String className)
            throws Exception, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> ctor = Class.forName(name).getDeclaredConstructor(new Class[]{ClassVisitor.class, String.class});
        ctor.setAccessible(true);
        return (T) ctor.newInstance(new Object[]{cv, className});
    }

}
