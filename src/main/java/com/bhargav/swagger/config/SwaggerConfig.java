/**
 * 
 */
package com.bhargav.swagger.config;

import java.io.IOException;
import java.security.Principal;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.controllers.DefaultSwaggerController;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.Model;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @author nsrikantaiah
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

  @Autowired
  private SpringSwaggerConfig springSwaggerConfig;

  private ApiInfo apiInfo() {
    String appTitle = null;
    String appDescription = null;
    String appContactEmail = null;
    String appLicenseType = null;
    String appLicenseURL = null;
    Properties properties = new Properties();
    try {
      properties.load(this.getClass().getClassLoader().getResourceAsStream("swagger.properties"));
      appTitle = properties.getProperty("application.api.title");
      appContactEmail = properties.getProperty("application.api.email");
      appDescription = properties.getProperty("application.api.description");
      appLicenseType = properties.getProperty("application.api.licenseType");
      appLicenseURL = properties.getProperty("application.api.licenseLocation");
    } catch (IOException ioe) {
      LOGGER.error("Failed to load swagger.properties. ErrMessage: {}", ioe.getMessage(), ioe);
    }
    return new ApiInfo(appTitle, appDescription, null, appContactEmail, appLicenseType,
        appLicenseURL);
  }

  @Bean
  public SwaggerSpringMvcPlugin customImplementation() {
    SwaggerSpringMvcPlugin config = new SwaggerSpringMvcPlugin(this.springSwaggerConfig);
    config.apiInfo(apiInfo());
    config.ignoredParameterTypes(Principal.class);
    config.ignoredParameterTypes(HttpServletRequest.class);
    config.ignoredParameterTypes(HttpServletResponse.class);
    config.ignoredParameterTypes(Model.class);
    SwaggerPathProvider provider = new SwaggerPathProvider() {
      @Override
      protected String applicationPath() {
        return getAppRoot().build().toString();
      }

      private UriComponentsBuilder getAppRoot() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:9090/swagger-int");
      }

      @Override
      protected String getDocumentationPath() {
        return getAppRoot().path(DefaultSwaggerController.DOCUMENTATION_BASE_PATH).build()
            .toString();
      }
    };
    config.pathProvider(provider);
    return config;
  }

}
