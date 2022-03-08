package indi.tiandi;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * @author tiandi
 * @date 2022/3/4
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface Column {
    String name() default "";

    int index() default 0;
}
