package DecoratorPattern;

import Constants.ApplicationConstants;

public class ChristmasTree implements IChristmasTree {
    @Override
    public String decorate() {
        return ApplicationConstants.BASIC_TREE;
    }

    @Override
    public float cost() {
        return 0;
    }
}
