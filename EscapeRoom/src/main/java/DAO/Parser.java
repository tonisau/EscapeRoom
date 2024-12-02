package DAO;

import connections.callback.ParsingCallback;
import connections.attribute.outputAttribute.OutputAttribute;

import java.util.HashSet;

public class Parser<T> {

    ParsingCallback<T> callback;

    public Parser(ParsingCallback<T> callback) {
        this.callback = callback;
    }

    public void parseObject(T object, HashSet<OutputAttribute> values) {
        for (OutputAttribute attribute: values) {
            switch (attribute.getType()) {
                case STRING -> callback.onCallbackString(object, attribute);
                case INT -> callback.onCallbackInt(object, attribute);
                default -> {
                }
            }
        }
    }
}
