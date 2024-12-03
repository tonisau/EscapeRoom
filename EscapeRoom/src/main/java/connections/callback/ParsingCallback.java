package connections.callback;

import classes.enums.Material;
import classes.enums.Theme;
import connections.attribute.Attribute;

public interface ParsingCallback<T> {
    public void onCallbackString(T object, Attribute<String> attribute);
    public void onCallbackInteger(T object, Attribute<Integer> attribute);
	public void onCallbackDouble(T object, Attribute<Double> attribute);
    public void onCallbackMaterial(T object, Attribute<Material> attribute);
    public void onCallbackTheme(T object, Attribute<Theme> attribute);
}
