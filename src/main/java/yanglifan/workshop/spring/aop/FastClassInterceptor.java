package yanglifan.workshop.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;

/**
 * @author Yang Lifan
 */
public class FastClassInterceptor implements MethodInterceptor {
    FastMethod fastMethod;
    UserService userService = new UserService();

    public FastClassInterceptor() throws NoSuchMethodException {
        FastClass fastClass = FastClass.create(UserService.class);
        fastMethod = fastClass.getMethod(UserService.class.getMethod("add"));
    }

    public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
        if (method.getName().toLowerCase().contains("add")) {
            return fastMethod.invoke(userService, new Object[0]);
        }
        return obj;
    }
}
