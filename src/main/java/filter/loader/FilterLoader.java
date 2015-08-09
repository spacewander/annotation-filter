package filter.loader;

import filter.After;
import filter.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zexuan.lzx on 2015/8/9.
 */
public class FilterLoader {
    private Object target;
    private Class metaData;

    public FilterLoader(Object filterObject) {
        target = filterObject;
        metaData = filterObject.getClass();
    }

    public void runMethod(String methodName,  Object... params) {
        try {
            int length = params.length;
            Class[] parameterTypes = new Class[length];
            for (int i = 0; i < length; i++) {
                parameterTypes[i] = params[i].getClass();
            }
            Method method = metaData.getDeclaredMethod(methodName, parameterTypes);

            // invoke before filters
            Before before = method.getAnnotation(Before.class);
            if (before != null) {
                String[] filters = before.methods();
                if (filters != null && filters.length > 0) {
                    for (String filter : filters) {
                        metaData.getDeclaredMethod(filter).invoke(target);
                    }
                }
            }

            // invoke method
            method.invoke(target, params);

            // invoke after filters
            After after = method.getAnnotation(After.class);
            if (after != null) {
                String[] filters = after.methods();
                if (filters != null && filters.length > 0) {
                    for (String filter : filters) {
                        metaData.getDeclaredMethod(filter).invoke(target);
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
