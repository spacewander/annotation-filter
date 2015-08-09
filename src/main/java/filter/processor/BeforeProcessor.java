package filter.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zexuan.lzx on 2015/8/7.
 */
//@SupportedAnnotationTypes({"filter.Before"})
public class BeforeProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                processAnnotation(e, messager);
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private static void processAnnotation(Element e, Messager messager) {
        Object[] methods = findAnnotationValueForList(e, "filter.Before", "methods");
        if (methods != null && methods.length > 0) {
            messager.printMessage(Diagnostic.Kind.NOTE, methods[0].toString());
        }
        messager.printMessage(Diagnostic.Kind.NOTE, e.toString());
    }

    private static Object[] findAnnotationValueForList(Element element, String annotationClass,
                                             String valueName) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            TypeElement annotationElement = (TypeElement) annotationType
                    .asElement();
            // figure out target annotation
            if (annotationElement.getQualifiedName().contentEquals(
                    annotationClass)) {
                return extractValueForList(annotationMirror, valueName);
            }
        }
        return null;
    }

    private static Object[] extractValueForList(AnnotationMirror annotationMirror,
                                      String valueName) {
        Map<ExecutableElement, AnnotationValue> elementValues = new HashMap<>(
                annotationMirror.getElementValues());
        for (Map.Entry<ExecutableElement, AnnotationValue> entry : elementValues.entrySet()) {
            if (entry.getKey().getSimpleName().contentEquals(valueName)) {
                Object value = entry.getValue().getValue();
                return AbstractCollection.class.cast(value).toArray();
            }
        }
        return null;
    }
}
