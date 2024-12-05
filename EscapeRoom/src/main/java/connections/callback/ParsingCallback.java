package connections.callback;

import classes.enums.Level;
import classes.enums.Material;
import classes.enums.Theme;
import connections.attribute.Attribute;

public interface ParsingCallback<T> {
    void onCallbackString(T object, Attribute<String> attribute);
    void onCallbackInteger(T object, Attribute<Integer> attribute);
	void onCallbackDouble(T object, Attribute<Double> attribute);
    void onCallbackBoolean(T object, Attribute<Boolean> attribute);
}
