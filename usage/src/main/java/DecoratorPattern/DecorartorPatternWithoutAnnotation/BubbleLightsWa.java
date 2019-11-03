package DecoratorPattern.DecorartorPatternWithoutAnnotation;

import DecoratorPattern.IChristmasTree;
import DecoratorPattern.TreeDecorator;

public class BubbleLightsWa extends TreeDecorator {
    public BubbleLightsWa(IChristmasTree tree) {
        super(tree);
    }
    public String decorate() {
        return super.decorate() + decorateWithBubbleLight();
    }

    public String decorateWithBubbleLight() {
        return " with **Bubble Lights** ";
    }
}
