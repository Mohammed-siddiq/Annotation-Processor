package DecoratorPattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Captures details about the class annotated with @decorator
 */
public class DecoratorAnnotatedClass {
    private TypeElement annotatedClassElement;
    private String name;
    private String decorator;
    private String qualifierClassName;
    private String simpleDecoratedTypeName;
    private String implementingType;


    private String qualifiedDecoratedGroupType;


    Logger logger = LoggerFactory.getLogger(DecoratorAnnotatedClass.class);

    public DecoratorAnnotatedClass(TypeElement annotatedClassElement) throws DecoratorException {
        this.annotatedClassElement = annotatedClassElement;
        Decorator decoratorAnnotation = annotatedClassElement.getAnnotation(Decorator.class);


        name = decoratorAnnotation.name();
        logger.info("Decorator name: " + name);
        qualifierClassName = name;
        decorator = decoratorAnnotation.decorate();
        logger.info("Decorator : " + decorator);

        try {
            Class<?> clazz = decoratorAnnotation.type();
            qualifiedDecoratedGroupType = clazz.getCanonicalName();
            simpleDecoratedTypeName = clazz.getSimpleName();

        } catch (MirroredTypeException mte) {
//            logger.info("Exception occured: ", mte);
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedDecoratedGroupType = classTypeElement.getQualifiedName().toString();
            simpleDecoratedTypeName = classTypeElement.getSimpleName().toString();
        }

        try{
            Class<?> clazz =  decoratorAnnotation.implementingType();
            implementingType = clazz.getCanonicalName();
        }
        catch (MirroredTypeException mte) {
//            logger.info("Exception occured: ", mte);
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            implementingType = classTypeElement.getQualifiedName().toString();
        }
        logger.info("Qualified group class name : " + qualifiedDecoratedGroupType);
        logger.info("Simple group name : " + simpleDecoratedTypeName);

        logger.info("Implementing type : " + implementingType);

        // Check if name is specified for the decorator class
        if (name.length() == 0) {
            throw new DecoratorException(annotatedClassElement,
                    "decorate() in @%s for class %s iw empty! Invalid use of Decorator pattern",
                    Decorator.class.getSimpleName(), annotatedClassElement.getQualifiedName().toString());
        }
        if (decorator.length() == 0){
            throw new DecoratorException(annotatedClassElement,
                    "decorate() in @%s for class %s is empty! Invalid use of Decorator pattern",
                    Decorator.class.getSimpleName(), annotatedClassElement.getQualifiedName().toString());

        }
    }

    /**
     * Get the name as specified in {@link Decorator#decorate()} ()}.
     * return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the full qualified name of the type specified in  {@link Decorator#type()}.
     *
     * @return qualified name
     */
    public String getQualifiedDecoratorName() {
        return qualifierClassName;
    }

    /**
     * Get the simple name of the type specified in  {@link Decorator#type()}.
     *
     * @return qualified name
     */
    public String getDecorator() {
        return decorator;
    }

    /**
     * The original element that was annotated with @Decorator
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }

    public Class getQualifiedDecoratedGroupTypeClass() {
        try {
            logger.info("Returning class for : " + qualifiedDecoratedGroupType);
            return Class.forName(qualifiedDecoratedGroupType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQualifiedDecoratedGroupType() {
        return qualifiedDecoratedGroupType;
    }

    public String getSimpleDecoratedTypeName() {
        return simpleDecoratedTypeName;
    }

    public String getImplementingType() {
        return implementingType;
    }
}
