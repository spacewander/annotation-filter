package filter.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zexuan.lzx on 2015/8/10.
 */
public abstract class Wrapper {
    protected Object target;
    protected Class metaData;
    protected Method method;
    protected Object[] params;

    // 如果限定filtedObject为interface类型，可以使用java.reflect.Proxy，这个用起来更加自然。也是Spring AOP中的做法。
    public Wrapper(Object filtedObject) {
        target = filtedObject;
        metaData = filtedObject.getClass();
    }

    protected void prepareMethod(String methodName,  Object... params) throws NoSuchMethodException {
        this.params = params; // store params so that we can use them in invoke

        int length = params.length;
        Class[] parameterTypes = new Class[length];
        for (int i = 0; i < length; i++) {
            parameterTypes[i] = params[i].getClass();
        }
        method = metaData.getDeclaredMethod(methodName, parameterTypes);
    }

    /**
     * invoke method
     */
    protected void invoke() throws IllegalAccessException, InvocationTargetException {
        method.invoke(target, params);
    }

    abstract public void runMethod(String methodName,  Object... params);
}
