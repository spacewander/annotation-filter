package filter.wrapper;

import filter.BeforeAction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zexuan.lzx on 2015/8/10.
 */
public class ControllerWrapper extends Wrapper {
    // To keep the order of method(from top to down), use LinkedHashMap
    private Map<Method, Set<String>> beforeFilterMapper = new LinkedHashMap<>();

    public ControllerWrapper(Object filtedObject) {
        super(filtedObject);
        for (Method method : metaData.getDeclaredMethods()) {
            BeforeAction beforeAction = method.getAnnotation(BeforeAction.class);
            if (beforeAction != null) {
                String[] methods = beforeAction.only();
                Set<String> filtedMethods = new HashSet<>();
                filtedMethods.addAll(Arrays.asList(methods));
                beforeFilterMapper.put(method, filtedMethods);
            }
        }
    }

    @Override
    public void runMethod(String methodName, Object... params) {
        try {
            prepareMethod(methodName, params);
            for (Map.Entry<Method, Set<String>> filterEntry : beforeFilterMapper.entrySet()) {
                if (filterEntry.getValue().contains(methodName)) {
                    filterEntry.getKey().invoke(target);
                }
            }
            invoke();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
