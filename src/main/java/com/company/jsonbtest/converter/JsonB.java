package com.company.jsonbtest.converter;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Transient;

import java.util.HashMap;
import java.util.Map;

@JmixEntity(annotatedPropertiesOnly = true)
public class JsonB {
    private Map<String, Object> container = new HashMap<>();

    public Map<String, Object> getContainer() {
        return container;
    }

    public void setContainer(Map<String, Object> container) {
        this.container = container;
    }
}
