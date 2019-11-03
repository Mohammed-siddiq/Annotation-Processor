package DecoratorPattern;

import Constants.ConfigReader;
import com.google.auto.service.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;


@AutoService(Processor.class)
public class DecoratorProcessor extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(DecoratorProcessor.class);

    private Types types;
    private Elements elements;
    private Filer filer;
    private Messager messager;
    private Map<String, Decorators> decoratedClasses = new LinkedHashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        types = processingEnv.getTypeUtils();
        elements = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Decorator.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Validates the annotated decorator class
     *
     * @param item The item that's annotated with @Decorator
     * @throws DecoratorException
     */
    void validateAnnotatedClass(DecoratorAnnotatedClass item) throws DecoratorException {

        logger.info("Validating annotated class :" + item);
        TypeElement classElement = item.getTypeElement();


        // 1: validate that the annotated decorator class is public
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
//            if (ConfigReader.RAISE_WARNING_ON_VIOLATION) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Class " + classElement.getQualifiedName().toString() + "is not public, Class must be public");
//            }
//            if (ConfigReader.RAISE_ERROR_ON_VIOLATION)
//                throw new DecoratorException(classElement, "Class %s is not public, Class must be public",
//                        classElement.getQualifiedName().toString());
        }

        //2: Check if the class is an abstract class

        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
//            if (ConfigReader.RAISE_ERROR_ON_VIOLATION)
//                throw new DecoratorException(classElement, "Class %s is abstract, Class must be concrete",
//                        classElement.getQualifiedName().toString());
//            if (ConfigReader.RAISE_WARNING_ON_VIOLATION)
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Class " + classElement.getQualifiedName().toString() + "is Abstract, Class must be concrete");

        }

        if (classElement.getModifiers().contains(Modifier.STATIC)) {
//            if (ConfigReader.RAISE_WARNING_ON_VIOLATION)
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Class " + classElement.getQualifiedName().toString() + "is static, Class must be non static");
        }

        if (!classElement.getSuperclass().toString().equals("java.lang.Object")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Class " + classElement.getQualifiedName().toString() + " is extending" +
                    classElement.getSuperclass());

        }


    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Set<Element> allElements = (Set<Element>) roundEnv.getElementsAnnotatedWith(Decorator.class);

            logger.info("All annotated classes : " + allElements);

            // Scan the classes annotated with decorator
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Decorator.class)) {

                logger.info("Scanning Class : " + annotatedElement);
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new DecoratorException(annotatedElement, "Can only annotated Classes with @%s", Decorator.class.getSimpleName());
                }
                TypeElement typeElement = (TypeElement) annotatedElement;

                logger.info("Instantiating Decorator Annotated.. ");
                DecoratorAnnotatedClass annotatedClass = new DecoratorAnnotatedClass(typeElement);


                //validate annotation
                validateAnnotatedClass(annotatedClass);

                logger.info("Updating map with " + annotatedClass.getName());

                Decorators decorators = decoratedClasses.get(annotatedClass.getQualifiedDecoratorName());
                if (decorators == null) {
                    String qualifiedDecoratedType = annotatedClass.getQualifiedDecoratedGroupType();
                    decorators = new Decorators(qualifiedDecoratedType);
                    decoratedClasses.put(qualifiedDecoratedType, decorators);

                }

                decorators.add(annotatedClass);
                decorators.generateCode(elements, filer);

                logger.info("Updated Map : " + decoratedClasses);

            }

            //Generate Code

//            for (Decorators decorators : decoratedClasses.values()) {
//                logger.info("Generating code for decorators of type : " + decorators);
//                decorators.generateCode(elements, filer);
//            }

            decoratedClasses.clear();

        } catch (DecoratorException ex) {
            printErrorMessage(ex.getElement(), ex.getMessage());

        } catch (Exception ex) {

        }
        return true;
    }

    /**
     * Prints the error message
     *
     * @param element Element which caused the error
     * @param message the error message
     */
    private void printErrorMessage(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
