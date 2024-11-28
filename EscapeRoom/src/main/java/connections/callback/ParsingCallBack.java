package connections.callback;

import connections.query.resultAttribute.Attribute;

public interface ParsingCallBack<T> {
    public void onCallbackString(T object, Attribute attribute);
    public void onCallbackInt(T object, Attribute attribute);
}
