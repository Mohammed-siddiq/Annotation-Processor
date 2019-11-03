package DecoratorPattern;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class generates and describes the {@link DecoratorAnnotatedClass}s that belongs to one Decorator.
 */

public class Decorators {
    /**
     * Will be added to the name of the generated factory class
     */
//    private static final String SUFFIX = "Decorator";

    Logger logger = LoggerFactory.getLogger(Decorators.class);

    private String qualifiedDecoratedType;
//    private String decoratorName;
//    private String decoratedWith;
//    private Class decoratorType;

    private Map<String, DecoratorAnnotatedClass> itemsMap =
            new LinkedHashMap<>();

    public Decorators(String qualifiedDecoratedType) {
        this.qualifiedDecoratedType = qualifiedDecoratedType;
    }

    /**
     * Adds an annotated class to this Decorator.
     *
     * @throws DecoratorException if another annotated class with the same id is
     *                            already present.
     */
    public void add(DecoratorAnnotatedClass toInsert) throws DecoratorException {

        DecoratorAnnotatedClass existing = itemsMap.get(toInsert.getName());
        if (existing != null) {

            // Already existing
            throw new DecoratorException(toInsert.getTypeElement(),
                    "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                    toInsert.getTypeElement().getQualifiedName().toString(), Decorator.class.getSimpleName(),
                    toInsert.getName(), existing.getTypeElement().getQualifiedName().toString());
        }
        logger.info("Inserting into decorated items map {} {} ", toInsert.getName(), toInsert);
        itemsMap.put(toInsert.getName(), toInsert);
    }

    //Need to override to decorate instead of creating a factory
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {


        logger.info("Items size {} ", itemsMap.size());
        logger.info("Map items : " + itemsMap.values());
        logger.info("Map Keys : " + itemsMap.keySet());

        for (DecoratorAnnotatedClass decoratorAnnotatedClass : itemsMap.values()) {

            logger.info("code for class : " + decoratorAnnotatedClass.getTypeElement().getQualifiedName());
            TypeElement className = elementUtils.getTypeElement(decoratorAnnotatedClass.getTypeElement().getQualifiedName());
            String decoratorClassName = className.getSimpleName().toString();

            logger.info("DecoratorClassName : " + decoratorClassName);
//        String qualifiedDecoratorClassName = qualifiedDecoratedType + SUFFIX;
            PackageElement pkg = elementUtils.getPackageOf(className);
            String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

            logger.info("Package Name : " + packageName);


            TypeElement superClassName = elementUtils.getTypeElement(decoratorAnnotatedClass.getQualifiedDecoratedGroupType());
            TypeElement implementingSuperClass = elementUtils.getTypeElement(decoratorAnnotatedClass.getImplementingType());

            logger.info("Super class Name : " + superClassName);
            //constructor invoking super to build the basic object around which decorator is applied
            MethodSpec.Builder constructor = generateConstructor(decoratorAnnotatedClass.getSimpleDecoratedTypeName().toLowerCase(), TypeName.get(superClassName.asType()));
            logger.info("Constructor : " + constructor.build().toString());

            String decoratedMethodName = "decorateWith" + decoratorAnnotatedClass.getName();
            logger.info("DecoratedMethodName : " + decoratedMethodName);


            MethodSpec.Builder decoratorMethod = MethodSpec.methodBuilder(decoratedMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class);

            decoratorMethod.addStatement("return $S", decoratorAnnotatedClass.getDecorator());

            logger.info("Decorated Method : " + decoratorMethod.build().toString());


            MethodSpec.Builder overriddenMethod = MethodSpec.methodBuilder("decorate")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class);


            overriddenMethod.addStatement("return super.decorate() + $L()", decoratedMethodName);

            logger.info("overridden Method : " + overriddenMethod.build().toString());

            TypeSpec typeSpec = TypeSpec.classBuilder(decoratorAnnotatedClass.getName()).addModifiers(Modifier.PUBLIC).superclass(TypeName.get(implementingSuperClass.asType()))
                    .addMethod(overriddenMethod.build()).
                            addMethod(constructor.build()).addMethod(decoratorMethod.build()).build();

            logger.info("Generating file: " + typeSpec.toBuilder().build().toString());

            // Write file
            JavaFile.builder(packageName, typeSpec).build().writeTo(filer);

        }
    }

    private MethodSpec.Builder generateConstructor(String parameterName, TypeName parameterType) {
        MethodSpec.Builder method = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(parameterType, parameterName);
        method.addStatement("super(" + parameterName + ")");
        return method;
    }

    public String getQualifiedDecoratedType() {
        return qualifiedDecoratedType;
    }


}
