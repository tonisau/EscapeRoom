package connections.query.resultAttribute;

public class AttributeValue<T> {

    T value;

    public AttributeValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AttributeValue{" +
                "value=" + value +
                '}';
    }
}
