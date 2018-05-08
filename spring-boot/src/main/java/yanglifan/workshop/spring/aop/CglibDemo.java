package yanglifan.workshop.spring.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Yang Lifan
 */
public class CglibDemo {
    @Test
    public void demoEnhancer() throws NoSuchMethodException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new FastClassInterceptor());
        UserService userService = (UserService) enhancer.create();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            userService.add();
        }
        System.out.println("Enhancer call need " + (System.currentTimeMillis() - start));

        userService = new UserService();
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            userService.add();
        }
        System.out.println("Raw call need " + (System.currentTimeMillis() - start));
    }

    @Test
    public void demoFastClass() throws NoSuchMethodException, InvocationTargetException {
        FastClass fastClass = FastClass.create(UserService.class);
        FastMethod fastMethod = fastClass.getMethod(UserService.class.getMethod("add"));

        UserService userService = new UserService();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            fastMethod.invoke(userService, new Object[0]);
        }
        System.out.println("FastClass call need " + (System.currentTimeMillis() - start));
    }
}

