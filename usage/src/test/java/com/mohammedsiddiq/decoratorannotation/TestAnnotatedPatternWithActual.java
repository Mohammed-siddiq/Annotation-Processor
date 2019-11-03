package com.mohammedsiddiq.decoratorannotation;

import DecoratorPattern.*;
import DecoratorPattern.DecorartorPatternWithoutAnnotation.BubbleLightsWa;
import DecoratorPattern.DecorartorPatternWithoutAnnotation.ElectricLightsWa;
import DecoratorPattern.DecorartorPatternWithoutAnnotation.GarlandWa;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAnnotatedPatternWithActual {

    Logger logger = LoggerFactory.getLogger(TestAnnotatedPatternWithActual.class);

    @Test
    public void verifySingleDecoration() {

        logger.info("Decorating once with Annotations");
        IChristmasTree christmasTreeA = new Garland(new ChristmasTree());

        logger.info("Decorating once without Annotations");

        IChristmasTree christmasTreeWa = new GarlandWa(new ChristmasTree());

        Assert.assertEquals(christmasTreeA.decorate(), christmasTreeWa.decorate());
    }

    @Test
    public void verifyMultipleDecoration() {

        logger.info("Decorating Multiple times with Annotations");

        IChristmasTree christmasTreeA = new BubbleLight(new Garland(new ChristmasTree()));
        logger.info("Annotated decoration : {} ", christmasTreeA.decorate());


        logger.info("Decorating Multiple times without Annotations");
        IChristmasTree christmasTreeWa = new BubbleLightsWa(new GarlandWa(new ChristmasTree()));
        logger.info("Non annotated decoration : {} ", christmasTreeWa.decorate());

        Assert.assertEquals(christmasTreeA.decorate(), christmasTreeWa.decorate());
//        System.out.println(christmasTreeA.decorate());


    }

    @Test
    public void verifyRandomDecoration(){

        logger.info("Decorating Multiple times without Annotations");
        IChristmasTree christmasTreeWa = new GarlandWa(new ElectricLightsWa(new GarlandWa(new ElectricLightsWa(new ChristmasTree()))));
        logger.info("Non annotated decoration : {} ", christmasTreeWa.decorate());


        logger.info("Decorating Multiple times with Annotations");
        IChristmasTree christmasTreeA = new Garland(new ElectricLight(new Garland(new ElectricLight(new ChristmasTree()))));
        logger.info("Annotated decoration : {} ", christmasTreeA.decorate());

        Assert.assertEquals(christmasTreeA.decorate(), christmasTreeWa.decorate() );
    }
}
