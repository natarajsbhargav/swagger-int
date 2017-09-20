/**
 * 
 */
package com.bhargav.swagger.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhargav.swagger.beans.SystemProperty;
import com.wordnik.swagger.annotations.Api;

/**
 * @author nsrikantaiah
 */
@RestController
@RequestMapping(value = "/api/systemProperty")
@Api(value = "SystemPropertyController", description = "System Property API")
public class SystemPropertyController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemPropertyController.class);

  @RequestMapping(value = {"/getValue"}, method = {RequestMethod.GET},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public SystemProperty getSystemProperty(@RequestParam String variable)
      throws ReflectiveOperationException {
    System.out.println("variable: " + variable);
    LOGGER.info("variable: {}", variable);
    return new SystemProperty(variable, UUID.randomUUID().toString());
  }

  @RequestMapping(value = {"/setValue"}, method = {RequestMethod.POST},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public SystemProperty setSystemProperty(@RequestParam String variable, @RequestParam String value)
      throws ReflectiveOperationException {
    System.out.println("variable: " + variable + ", value: " + value);
    LOGGER.info("variable: {}, value: {}", variable, value);
    return new SystemProperty(variable, "Updated: " + value);
  }

}
