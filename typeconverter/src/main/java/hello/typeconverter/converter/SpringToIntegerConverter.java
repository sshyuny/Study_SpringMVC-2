package hello.typeconverter.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class SpringToIntegerConverter implements Converter<String, Integer> {

    @Override
    @Nullable
    public Integer convert(String source) {
        return null;
    }
    
}
