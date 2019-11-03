package com.mohammedsiddiq.decoratorannotation;

import DecoratorPattern.*;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDecorator {

    Logger logger = LoggerFactory.getLogger(TreeDecorator.class);


    @Test
    public void verifyNoDecoration(){
        String testString = "Simple Christmas Tree!";
        IChristmasTree christmasTree = new ChristmasTree();
        Assert.assertEquals(christmasTree.decorate(), testString);
    }

    @Test
    public void verifySingleDecoration() {
        String testString = "Simple Christmas Tree! with {Garlands} ";

        IChristmasTree christmasTree = new Garland(new ChristmasTree());
        Assert.assertEquals(christmasTree.decorate(), testString);
    }

    @Test
    public void verifyMultipleDecoration() {

        String testString = "Simple Christmas Tree! with {Garlands}  with **Bubble Lights** ";
        IChristmasTree christmasTree = new BubbleLight(new Garland(new ChristmasTree()));
        Assert.assertEquals(christmasTree.decorate(), testString);

    }

    @Test
    public void verifyRandomDecoration(){
        String testString = "Simple Christmas Tree! with ~~Electric Lights~~  with {Garlands}  with ~~Electric Lights~~  with {Garlands} ";
        IChristmasTree christmasTree = new Garland(new ElectricLight(new Garland(new ElectricLight(new ChristmasTree()))));
        Assert.assertEquals(christmasTree.decorate(), testString );
    }


}
