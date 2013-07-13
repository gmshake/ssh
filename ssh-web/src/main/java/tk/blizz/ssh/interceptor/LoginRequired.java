package tk.blizz.ssh.interceptor;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that the class is authentication required. This annotation is
 * applied to the entity class.
 * 
 * @author zlei.huang@gmail.com
 * 
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface LoginRequired {
	boolean loginRequired() default true;
}
