package example;

import filter.BeforeAction;

/**
 * Created by zexuan.lzx on 2015/8/10.
 */
public class ActionController {
    @BeforeAction(only = {"create", "edit"})
    public void requireLogin() {
        System.out.println("login");
    }

    @BeforeAction(only = {"create"})
    public void validate() {
        System.out.println("validate");
    }

    public void create() {
        System.out.println("create something");
    }

    public void show() {
        System.out.println("show something");
    }

    public void edit() {
        System.out.println("edit something");
    }
}
