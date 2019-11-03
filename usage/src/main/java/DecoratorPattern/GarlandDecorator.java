package DecoratorPattern;


import Constants.ApplicationConstants;

@Decorator(
        name = ApplicationConstants.GARLAND_DECORATOR,
        type = IChristmasTree.class,
        decorate = " with {Garlands} ",
        costOfDecorating = 40,
        implementingType = TreeDecorator.class
)
public class GarlandDecorator {
}
