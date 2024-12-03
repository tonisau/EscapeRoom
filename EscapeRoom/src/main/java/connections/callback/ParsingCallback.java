package connections.callback;

import classes.enums.Material;
import connections.attribute.Attribute;

public interface ParsingCallback<T> {
    void onCallbackString(T object, Attribute<String> attribute);
    void onCallbackInt(T object, Attribute<Integer> attribute);
	void onCallbackDouble(T object, Attribute<Double> attribute);
    void onCallbackMaterial(T object, Attribute<Material> attribute);
    void onCallbackBoolean(T object, Attribute<Boolean> attribute);
}
