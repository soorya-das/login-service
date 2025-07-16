package ot.learning.loginservice.config;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper setModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(Conditions.isNotNull());

        Converter<String, LocalDateTime> toStringDate =
                new AbstractConverter<>() {
                    @Override
                    protected LocalDateTime convert(String source) {
                        return LocalDateTime.parse(
                                source, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                };
        mapper.createTypeMap(String.class, LocalDateTime.class);
        mapper.addConverter(toStringDate);

        Converter<LocalDate, String> toLocalDate =
                new AbstractConverter<>() {
                    @Override
                    protected String convert(LocalDate date) {
                        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                };
        mapper.createTypeMap(LocalDate.class, String.class);
        mapper.addConverter(toLocalDate);
        return mapper;
    }
}
