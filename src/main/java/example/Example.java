package example;

import filter.After;
import filter.Before;
import filter.loader.FilterLoader;

/**
 * Created by zexuan.lzx on 2015/8/9.
 */
public class Example {

    public void beforeFilter() {
        System.out.println("beforeFilter");
    }

    public void beforeFilter2() {
        System.out.println("beforeFilter2");
    }

    public void afterFilter() {
        System.out.println("afterFilter");
    }

    @Before(methods = {"beforeFilter"})
    @After(methods = {"afterFilter"})
    public void run() {
        System.out.println("run");
    }

    @Before(methods = {"beforeFilter", "beforeFilter2"})
    public void runWithParam(String s) {
        System.out.println("run with " + s);
    }

    public static void main(String[] param) {
        Example example = new Example();
//        example.run();
        FilterLoader filterLoader = new FilterLoader(example);
        filterLoader.runMethod("run");
        System.out.println("");
        filterLoader.runMethod("runWithParam", "param A");
    }
}
