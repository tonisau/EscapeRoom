package DAO;

import connections.callback.ParsingCallBack;
import connections.query.resultAttribute.Attribute;

import java.util.HashSet;
import java.util.Map;

public class Parser<T> {

    ParsingCallBack<T> callback;

    public Parser(ParsingCallBack<T> callback) {
        this.callback = callback;
    }

    public void parseObject(T object, HashSet<Attribute> values) {
        for (Attribute attribute: values) {
            switch (attribute.getType()) {
                case STRING -> callback.onCallbackString(object, attribute);
                case INT -> callback.onCallbackInt(object, attribute);
                default -> {
                }
            }
        }
    }
}
