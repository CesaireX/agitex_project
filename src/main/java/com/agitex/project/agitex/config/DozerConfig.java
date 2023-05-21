package com.agitex.project.agitex.config;


import com.agitex.project.agitex.DTO.clientDTO;
import com.agitex.project.agitex.model.client;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {

    private final BeanMappingBuilder builder =
            new BeanMappingBuilder() {
                @Override
                protected void configure() {

                      mapping(client.class, clientDTO.class,
                            TypeMappingOptions.wildcard(Boolean.TRUE))
;
                }
            };

    /**
     * builds the dozer mapper.
     *
     * @return Mapper
     */
    @Bean
    public Mapper buildDozerMapper() {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(builder)
                .build();
    }
}
