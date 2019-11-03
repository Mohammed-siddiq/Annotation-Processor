package DecoratorPattern.Constants;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigReader {
    static Config config = ConfigFactory.load("application.conf");
    public static final boolean RAISE_ERROR_ON_VIOLATION = config.getBoolean("ON_ANNOTATION_VIOLATED_ERROR");
    public static final boolean RAISE_WARNING_ON_VIOLATION = config.getBoolean("ON_ANNOTATION_VIOLATED_WARN");
    public static final boolean DECORATE_WITH_GARLAND = config.getBoolean("DECORATE_WITH_GARLAND");
    public static final boolean DECORATE_WITH_BUBBLE_LIGHT = config.getBoolean("DECORATE_WITH_BUBBLE_LIGHT");
    public static final boolean DECORATE_WITH_ELECTRIC_LIGHT = config.getBoolean("DECORATE_WITH_ELECTRIC_LIGHT");
    public static final boolean DECORATE_WITH_ALL = config.getBoolean("DECORATE_WITH_ALL");
}
