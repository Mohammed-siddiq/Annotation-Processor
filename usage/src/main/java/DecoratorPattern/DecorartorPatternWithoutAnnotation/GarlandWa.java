package DecoratorPattern.DecorartorPatternWithoutAnnotation;

import DecoratorPattern.IChristmasTree;
import DecoratorPattern.TreeDecorator;

public class GarlandWa extends TreeDecorator {
    public GarlandWa(IChristmasTree tree) {
        super(tree);
    }
    public String decorate() {
        return super.decorate() + decorateWithGarland();
    }

    public String decorateWithGarland() {
        return " with {Garlands} ";
    }
}
