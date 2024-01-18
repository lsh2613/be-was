package controller;

import model.Request;

public class StaticResourceController {
    private String type;

    public StaticResourceController(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public ModelView process(String uri) {
        return new ModelView("/static" + uri);
    }
}
