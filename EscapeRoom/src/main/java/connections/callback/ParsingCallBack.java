package connections.callback;

import connections.attribute.outputAttribute.OutputAttribute;

public interface ParsingCallback<T> {
    public void onCallbackString(T object, OutputAttribute attribute);
    public void onCallbackInt(T object, OutputAttribute attribute);
	public void onCallbackDouble(T object, OutputAttribute attribute);
    public void onCallbackMaterial(T object, OutputAttribute attribute);
}
