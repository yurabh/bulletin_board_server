package com.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class {@link WebInitializer} replaces web.xml
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    /**
     * This method is used for inheritance of context
     *
     * @return Class {@link Class[]}
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }


    /**
     * This method is used to set up where all beans are located
     *
     * @return Class {@link Class[]}.It marked as @Configuration
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ConfigurationApplication.class};
    }


    /**
     * This method is used to define where servlet will map (link)
     *
     * @return String {@link String} where servlet will map
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/myapp/*"};
    }
}
