package DecoratorPattern.DecorartorPatternWithoutAnnotation;

import DecoratorPattern.IChristmasTree;
import DecoratorPattern.TreeDecorator;

public class ElectricLightsWa extends TreeDecorator {
    public ElectricLightsWa(IChristmasTree tree) {
        super(tree);
    }
    public String decorate() {
        return super.decorate() + decorateWithElectricLight();
    }

    public String decorateWithElectricLight() {
        return " with ~~Electric Lights~~ ";
    }
}
