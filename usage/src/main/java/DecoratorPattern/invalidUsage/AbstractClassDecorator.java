//package DecoratorPattern.invalidUsage;
//
//import Constants.ApplicationConstants;
//import DecoratorPattern.Decorator;
//import DecoratorPattern.IChristmasTree;
//import DecoratorPattern.TreeDecorator;
//
//@Decorator(
//        name = ApplicationConstants.GARLAND_DECORATOR,
//        type = IChristmasTree.class,
//        decorate = "garland ",
//        costOfDecorating = 40,
//        implementingType = TreeDecorator.class
//)
//public abstract class AbstractClassDecorator extends TreeDecorator {
//
//    public AbstractClassDecorator(IChristmasTree tree) {
//        super(tree);
//    }
//
//    void invalidMethodUsage() {
//
//    }
//
//    @Decorator(
//            name = ApplicationConstants.GARLAND_DECORATOR,
//            type = IChristmasTree.class,
//            decorate = " with {Garlands} ",
//            costOfDecorating = 40,
//            implementingType = TreeDecorator.class
//    )
//    private class tesUsage {
//
//    }
//
//
//    @Decorator(
//            name = ApplicationConstants.GARLAND_DECORATOR,
//            type = IChristmasTree.class,
//            decorate = " with {Garlands} ",
//            costOfDecorating = 40,
//            implementingType = TreeDecorator.class
//    )
//    public static class testStatic {
//
//    }
//
//
//}
