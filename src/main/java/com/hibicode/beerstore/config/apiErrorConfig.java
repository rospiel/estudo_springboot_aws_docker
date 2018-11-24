package com.hibicode.beerstore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Classe de configuração para captação dos erros lançados
 * @author Rodrigo
 */
@Configuration
public class apiErrorConfig {

    /**
     * Bean de acesso ao arquivo de mensagens
     * @return
     */
    @Bean
    public MessageSource apiErrorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/api_errors");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

}
