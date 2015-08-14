package filter.wrapper;

import filter.After;
import filter.Before;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by zexuan.lzx on 2015/8/9.
 */
public class FilterWrapper extends Wrapper {
    public FilterWrapper(Object filtedObject) {
        super(filtedObject);
    }

    @Override
    public void runMethod(String methodName,  Object... params) {
        try {
            prepareMethod(methodName, params);
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

            invoke();

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
