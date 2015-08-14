package example;

import filter.wrapper.ControllerWrapper;
import filter.wrapper.FilterWrapper;

/**
 * Created by zexuan.lzx on 2015/8/10.
 */
public class Main {
    public static void runExample() {
        Example example = new Example();
//        example.run();
        FilterWrapper filterWrapper = new FilterWrapper(example);
        filterWrapper.runMethod("run");
        System.out.println("");
        filterWrapper.runMethod("runWithParam", "param A");
    }

    public static void runController() {
        ActionController actionController = new ActionController();
        ControllerWrapper controllerWrapper = new ControllerWrapper(actionController);
        controllerWrapper.runMethod("create");
        controllerWrapper.runMethod("show");
        controllerWrapper.runMethod("edit");
    }

    public static void main(String[] param) {
//        runExample();
        runController();
    }
}
