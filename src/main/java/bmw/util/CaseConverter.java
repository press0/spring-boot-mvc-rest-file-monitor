package bmw.util;

import java.beans.PropertyEditorSupport;

public class CaseConverter<T extends Enum<T>> extends PropertyEditorSupport {

    private final java.lang.Class<T> typeParameterClass;

    public CaseConverter(java.lang.Class<T> typeParameterClass) {
        super();
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        String upper = text.toUpperCase(); // or something more robust
        T value = T.valueOf(typeParameterClass, upper);
        setValue(value);
    }
}
