package DecoratorPattern;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)

public @interface Decorator {

    /**
     * Name of the Decorator
     */
    String name();

    /**
     * Specifies the name of the decorator class to be applied
     */
    Class type();

    /**
     * The decorator to decorate with
     */
    String decorate();

    /**
     * The cost for decorating
     */

    float costOfDecorating();

    Class implementingType();


}
