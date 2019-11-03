package DecoratorPattern;

/**
 * Tree decorator which which other will extend for decoration
 */
public abstract class TreeDecorator implements IChristmasTree {
    private IChristmasTree tree;

    public TreeDecorator(IChristmasTree tree) {
        this.tree = tree;
    }

    public String decorate() {
        return tree.decorate();
    }

    public float cost() {
        return tree.cost();
    }
}
