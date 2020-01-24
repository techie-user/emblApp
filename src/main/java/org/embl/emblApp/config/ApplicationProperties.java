package org.embl.emblApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Person App.
 * <p>
 * Properties are configured in the {@code application.properties} file.
  */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}
