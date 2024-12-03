package DAO;

import connections.attribute.Attribute;
import connections.callback.ParsingCallback;

import java.util.HashSet;

public class Parser<T> {

    ParsingCallback<T> callback;

    public Parser(ParsingCallback<T> callback) {
        this.callback = callback;
    }

    public void parseObject(T object, HashSet<Attribute> values) {
        for (Attribute attribute: values) {
            if (attribute.getValue() instanceof String) callback.onCallbackString(object, attribute);
            else if (attribute.getValue() instanceof Integer) callback.onCallbackInt(object, attribute);
            else if (attribute.getValue() instanceof Double) callback.onCallbackDouble(object, attribute);
        }
    }
}
