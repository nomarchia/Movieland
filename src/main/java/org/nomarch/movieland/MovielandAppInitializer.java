package org.nomarch.movieland;

import org.nomarch.movieland.web.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MovielandAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { MovielandApplicationContext.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebApplicationContext.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/api/v1/*" };
    }
}
