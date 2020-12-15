package io.kimmking.rpcfx.client;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class RemoteCall {

    //@BindingPriority(3)
    public static void sayHelloBar() {
        System.out.println("Holla in Bar!");
    }

    //@BindingPriority(2)
    public static void sayBar() {
        System.out.println("bar");
    }

    @RuntimeType
    public static void intercept(@Origin String method, @AllArguments Object[] args) {
        System.out.println(method);
        System.out.println(args[0]);
        System.out.println("I have intercepted a call");
       // return "Hello";
    }


}
