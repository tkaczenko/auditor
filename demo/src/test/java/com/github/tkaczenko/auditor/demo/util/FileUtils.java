package com.github.tkaczenko.auditor.demo.util;

import java.nio.charset.Charset;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

@UtilityClass
public final class FileUtils {

  private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

  @SneakyThrows
  public static String readSystemResource(String location) {
    return IOUtils.toString(
        RESOURCE_LOADER.getResource(location).getInputStream(), Charset.defaultCharset());
  }
}
