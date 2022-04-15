package cn.cdipcc.aerolite.server.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String AppName;

    @Value("${version}")
    private String version;

    @Value("${springfox.documentation.swagger-ui.enabled}")
    private Boolean enabled;

    private List<SecurityScheme> securitySchemes() {
        return Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> defaultAuth = Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
        return Lists.newArrayList(SecurityContext.builder()
                .securityReferences(defaultAuth)
                .operationSelector(selector -> selector.requestMappingPattern().matches("^(?!/api/auth/).*$"))
                .build()
        );
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.cdipcc.aerolite.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(AppName)
                .description(String.format("%s API", AppName))
                .version(version)
                .build();
    }
}
