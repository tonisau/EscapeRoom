package connections.callback;

import classes.EscapeRoom;
import connections.query.resultAttribute.Attribute;

public interface CallBack<T> {
    public void onCallbackString(T object, Attribute attribute);
    public void onCallbackInt(T object, Attribute attribute);
}
