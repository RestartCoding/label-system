package com.example.label.repository.converter;

import com.example.label.enums.LabelAuth;
import com.example.label.enums.LabelStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataJdbcConfig extends AbstractJdbcConfiguration {

    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        List<Converter> conversions = new ArrayList<>();
        conversions.add(LabelStatusConverter.INSTANCE);
        conversions.add(LabelAuthConverter.INSTANCE);
        return new JdbcCustomConversions(conversions);
    }

    @ReadingConverter
    enum LabelStatusConverter implements Converter<Integer, LabelStatus> {
        INSTANCE;

        @Override
        public LabelStatus convert(Integer n) {
            if (n == null) {
                return null;
            }
            return LabelStatus.of(n);
        }
    }


    @ReadingConverter
    enum LabelAuthConverter implements Converter<Integer, LabelAuth> {
        INSTANCE;

        @Override
        public LabelAuth convert(Integer code) {
            if (code == null) {
                return null;
            }
            return LabelAuth.of(code);
        }
    }
}
