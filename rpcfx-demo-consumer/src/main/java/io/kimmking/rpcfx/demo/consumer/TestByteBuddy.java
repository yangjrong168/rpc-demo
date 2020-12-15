package io.kimmking.rpcfx.demo.consumer;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class TestByteBuddy {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();

        Class<?> dynamicType = unloadedType.load(TestByteBuddy.class
                .getClassLoader())
                .getLoaded();

        System.out.println(dynamicType.newInstance().toString());
    }
}
