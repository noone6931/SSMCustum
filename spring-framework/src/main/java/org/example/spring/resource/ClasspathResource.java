package org.example.spring.resource;

import java.io.InputStream;

public class ClasspathResource implements Resource {

    private String location;

    public ClasspathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getResource() {
        if (location.startsWith("classpath:")) {
            location = location.substring(10);
        } else if (location.startsWith("classpath*:")) {
            location = location.substring(11);
        }
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
