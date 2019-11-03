# Annotation For Decorator Design Pattern 

This project contains the validation and implementation of decorator pattern using annotation.

It is multi-module project where :

- **annotation module** defines the decorator annotation, which can be used to annotate classes to be used as decorators.

- **processor module** implements the annotation processor for the decorator. The processor
    - Validates the annotated classes/entities
    - Raises errors/warnings (based on the given config) upon violating the decorators usage
    - Generates classes corresponding to the annotated classes as decorators.
    
    
- **usage module** implements the decorator pattern for a simple use case of decorating a christmas tree with and
without annotations. Demonstrating the equivalence of pattern usage with annotations.

The following is the definition of the annotation to decorate a christmas tree.

```java

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)

public @interface Decorator {

    /**
     * Name of the Decorator
     */
    String name();

    /**
     * Specifies the name of the decorator class to be applied
     */
    Class type();

    /**
     * The decorator to decorate with
     */
    String decorate();

    /**
     * The cost for decorating
     */

    float costOfDecorating();

    Class implementingType();


}
```

The following is sample usage: 

```java
@Decorator(
        name = ApplicationConstants.ELECTRIC_LIGHT_DECORATOR,
        type = IChristmasTree.class,
        decorate = " with ~~Electric Lights~~ ",
        costOfDecorating = 100,
        implementingType = TreeDecorator.class
)
```

##
Steps to run

- Clone this repo :
```
git clone https://Iam_MohammedSiddiq@bitbucket.org/Iam_MohammedSiddiq/mohammed_siddiq_474_hw2.git
```
```
mvn clean compile test
```

Will validate the usage of annotation and run the tests over annotated and non-annotated implementation

___
### Validations

The decorator processor validates the usage of the annotation and raises errors on violating the use of the annotation

- have a non zero length name

- have an implementing type

- have the decorator description

- must be applied to a class

- cannot be applied to static classes

- usage class should have public modifier

- should not be implementing or extending any other class

Violated errors : uncomment the _invalidUsage_ package under the usage module to have a invalid run.

```java
[WARNING] Class DecoratorPattern.invalidUsage.AbstractClassDecoratoris Abstract, Class must be concrete
[WARNING] Class DecoratorPattern.invalidUsage.AbstractClassDecorator.tesUsageis not public, Class must be public

[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.1:compile (default-compile) on project usage: Compilation failure
[ERROR] /Users/mohammedsiddiq/Downloads/CS474_HW2/decorator/usage/src/main/java/DecoratorPattern/invalidUsage/AbstractClassDecorator.java:[15,17] decorate() in @Decorator for class DecoratorPattern.invalidUsage.AbstractClassDecorator is empty! Invalid use of Decorator pattern
[ERROR] 

[WARNING] Class DecoratorPattern.invalidUsage.AbstractClassDecorator is extending DecoratorPattern.TreeDecorator
```

On successful validations the processor then generates corresponding java files.

sample constructed .java file for the above decorator usage 

```java
public class ElectricLight extends DecoratorPattern.TreeDecorator {
  public ElectricLight(DecoratorPattern.IChristmasTree ichristmastree) {
    super(ichristmastree);
  }

  public java.lang.String decorate() {
    return super.decorate() + decorateWithElectricLight();
  }

  public java.lang.String decorateWithElectricLight() {
    return " with ~~Electric Lights~~ ";
  }
}
```

The patterns annotated usage is verified with patterns non-annotated usage: 

```
2019-11-03 15:36:03 INFO  TestAnnotatedPatternWithActual:53 - Non annotated decoration : Simple Christmas Tree! with ~~Electric Lights~~  with {Garlands}  with ~~Electric Lights~~  with {Garlands}  
2019-11-03 15:36:03 INFO  TestAnnotatedPatternWithActual:58 - Annotated decoration : Simple Christmas Tree! with ~~Electric Lights~~  with {Garlands}  with ~~Electric Lights~~  with {Garlands}  
```
