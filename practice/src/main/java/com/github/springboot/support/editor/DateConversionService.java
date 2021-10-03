package com.github.springboot.support.editor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * Created by langle on 2018/4/1.
 */
public class DateConversionService implements Converter<String, Date> {

    ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public void setFormat(String format) {
        sdf.set(new SimpleDateFormat(format));
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return new Date();
        }
        try {
            return sdf.get().parse(source);
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
        }
    }
}
