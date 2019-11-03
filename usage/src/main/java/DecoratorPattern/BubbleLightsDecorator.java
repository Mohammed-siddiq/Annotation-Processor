package DecoratorPattern;


import Constants.ApplicationConstants;

@Decorator(
        name = ApplicationConstants.BUBBLE_LIGHT_DECORATOR,
        type = IChristmasTree.class,
        decorate = " with **Bubble Lights** ",
        costOfDecorating = 50,
        implementingType = TreeDecorator.class
)
public class BubbleLightsDecorator {
}
