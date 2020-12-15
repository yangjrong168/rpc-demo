package io.kimmking.rpcfx.demo.consumer;
import io.kimmking.rpcfx.demo.api.Order;
import io.kimmking.rpcfx.demo.api.UserService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ByteBuddyUnitTest {
    public static void main(String[] args) throws Exception {
        ByteBuddyUnitTest test= new ByteBuddyUnitTest();
        System.out.println("========");
       // test.givenFoo_whenRedefined_thenReturnFooRedefined();
        System.out.println("========");

       test.givenMethodName_whenDefineMethod_thenCreateMethod();
        System.out.println("========");

        //test.givenObject_whenToString_thenReturnHelloWorldString();
        System.out.println("========");
        //test.givenSayHelloFoo_whenMethodDelegation_thenSayHelloBar();
    }
    public void interfaceProxy() throws Exception {
        Class<?> type = new ByteBuddy().subclass(UserService.class).name("MyUserService").defineMethod("say", String.class, Modifier.PUBLIC).intercept(MethodDelegation.to(Bar.class)).defineField("x", String.class, Modifier.PUBLIC).make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();

        Method m = type.getDeclaredMethod("say", null);

        System.out.println(m.invoke(type.newInstance()));
        //System.out.println(Bar.sayHelloBar());
        System.out.println(type.getDeclaredField("x"));

    }
    public void givenObject_whenToString_thenReturnHelloWorldString() throws InstantiationException, IllegalAccessException {
        DynamicType.Unloaded unloadedType = new ByteBuddy().subclass(Object.class).method(ElementMatchers.isToString()).intercept(FixedValue.value("Hello World ByteBuddy!")).make();

        Class<?> dynamicType = unloadedType.load(getClass().getClassLoader()).getLoaded();

        System.out.println(dynamicType.newInstance().toString());
    }

    public void givenFoo_whenRedefined_thenReturnFooRedefined() throws Exception {
        ByteBuddyAgent.install();
        String method="sayHelloFoo";
        new ByteBuddy().redefine(Foo.class).method(named(method)).intercept(FixedValue.value("Hello Foo Redefined")).make().load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        Foo f = new Foo();
        System.out.println(f.sayHelloFoo());
    }

    public void givenSayHelloFoo_whenMethodDelegation_thenSayHelloBar() throws IllegalAccessException, InstantiationException {

        String r = new ByteBuddy().subclass(Foo.class).method(named("sayHelloFoo").and(isDeclaredBy(Foo.class).and(returns(String.class)))).intercept(MethodDelegation.to(Bar.class)).make().load(getClass().getClassLoader()).getLoaded().newInstance()
                .sayHelloFoo();
        System.out.println(r);
        System.out.println(Bar.sayHelloBar());
    }

    public void givenMethodName_whenDefineMethod_thenCreateMethod() throws Exception {
       /* Class<?> type = new ByteBuddy().subclass(Object.class).name("MyClassName").defineMethod("custom", String.class, Modifier.PUBLIC).intercept(MethodDelegation.to(Bar.class)).defineField("x", String.class, Modifier.PUBLIC).make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();

        Method m = type.getDeclaredMethod("custom", null);

        System.out.println(m.invoke(type.newInstance()));
        //System.out.println(Bar.sayHelloBar());
        System.out.println(type.getDeclaredField("x"));*/

        Class<?> type = new ByteBuddy().subclass(UserService.class).name("MyUserService").defineMethod("say", String.class, Modifier.PUBLIC).intercept(MethodDelegation.to(Bar.class)).defineField("x", String.class, Modifier.PUBLIC).make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();

        Method m = type.getDeclaredMethod("say", null);

        System.out.println(m.invoke(type.newInstance()));
        //System.out.println(Bar.sayHelloBar());
        System.out.println(type.getDeclaredField("x"));

    }

}
