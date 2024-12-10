package connections.attribute;

import java.util.Objects;

public class Attribute<T> {

    private String name;
    private T value;
    private Class<T> type;

    public Attribute(T value, Class<T> type) {
        this.value = value;
        this.type = type;
    }

    public Attribute(String name, T value, Class<T> type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Attribute() {}

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Attribute<?> attribute = (Attribute<?>) object;
        return Objects.equals(getName(), attribute.getName()) && Objects.equals(getValue(), attribute.getValue()) && Objects.equals(getType(), attribute.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue(), getType());
    }
}
