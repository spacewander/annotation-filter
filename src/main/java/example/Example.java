package example;

import filter.After;
import filter.Before;

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
}
