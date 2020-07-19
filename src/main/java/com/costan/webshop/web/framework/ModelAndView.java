package com.costan.webshop.web.framework;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String view;
    private final Map<String, Object> modelData = new HashMap<>();

    public void addData(String key, Object value) {
        modelData.put(key, value);
    }

    public void setView(String view) {
        this.view = view;
    }

    protected String getView() {
        return this.view;
    }

    protected Map<String, Object> getInternalModel() {
        return this.modelData;
    }
}
