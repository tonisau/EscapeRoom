package DAO;

import connections.callback.ParsingCallBack;
import connections.query.resultAttribute.Attribute;

import java.util.HashMap;
import java.util.Map;

public class Parser<T> {

    ParsingCallBack<T> callback;

    public Parser(ParsingCallBack<T> callback) {
        this.callback = callback;
    }

    public void parseObject(T object, HashMap<String, Attribute> values) {
        for (Map.Entry<String, Attribute> set: values.entrySet()) {
            Attribute attribute = set.getValue();
            switch (attribute.getType()) {
                case STRING -> callback.onCallbackString(object, attribute);
                case INT -> callback.onCallbackInt(object, attribute);
                default -> {
                }
            }
        }
    }
}
