package com.mohammedsiddiq.decoratorannotation;

import Constants.ApplicationConstants;
import DecoratorPattern.Decorator;
import DecoratorPattern.IChristmasTree;
import DecoratorPattern.TreeDecorator;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;


@Decorator(
        name = ApplicationConstants.ELECTRIC_LIGHT_DECORATOR,
        type = IChristmasTree.class,
        decorate = " with ~~Electric Lights~~ ",
        costOfDecorating = 100,
        implementingType = TreeDecorator.class
)
public class TestAnnotation {
    Annotation annotation = TestAnnotation.class.getAnnotation(Decorator.class);
    Logger logger = LoggerFactory.getLogger(TestAnnotatedPatternWithActual.class);

    @Test
    public void verifyAnnotation() {
        logger.info("Verify the annotation accessibility using reflection");
        if (annotation instanceof Decorator) {
            Decorator decoratorAnnotation = (Decorator) annotation;

            Assert.assertEquals("ElectricLight", decoratorAnnotation.name());
            Assert.assertEquals(" with ~~Electric Lights~~ ", decoratorAnnotation.decorate());
        }
    }

}
