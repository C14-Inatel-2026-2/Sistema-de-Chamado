package br.inatel.chamados.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ControleAcessoInterceptor controleAcessoInterceptor;

    public WebConfig(ControleAcessoInterceptor controleAcessoInterceptor) {
        this.controleAcessoInterceptor = controleAcessoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controleAcessoInterceptor);
    }
}
