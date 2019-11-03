package DecoratorPattern;

import Constants.ApplicationConstants;

@Decorator(
        name = ApplicationConstants.ELECTRIC_LIGHT_DECORATOR,
        type = IChristmasTree.class,
        decorate = " with ~~Electric Lights~~ ",
        costOfDecorating = 100,
        implementingType = TreeDecorator.class
)
public class ElectricLightsDecorator {
}
