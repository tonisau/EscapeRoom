package DAO;

import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
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
            else if (attribute.getValue() instanceof Integer) callback.onCallbackInteger(object, attribute);
            else if (attribute.getValue() instanceof Double) callback.onCallbackDouble(object, attribute);
            else if (attribute.getValue() instanceof Theme) callback.onCallbackTheme(object, attribute);
            else if (attribute.getValue() instanceof Material) callback.onCallbackMaterial(object, attribute);
            else if (attribute.getValue() instanceof Boolean) callback.onCallbackBoolean(object, attribute);
            else if (attribute.getValue() instanceof Level) callback.onCallbackLevel(object, attribute);

        }
    }
}
