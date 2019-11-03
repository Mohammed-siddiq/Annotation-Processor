package DecoratorPattern;

import javax.lang.model.element.Element;

/**
 * Custom Generic Exception for capturing any during processing @decorator annotations
 */
public class DecoratorException extends Exception {
    private Element element;

    public DecoratorException(Element annotatedClassElement, String message, Object... args) {
        super(String.format(message, args));
        this.element = annotatedClassElement;
    }

    public Element getElement() {
        return element;
    }

}
