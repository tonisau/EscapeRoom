package connections.query.resultAttribute;

import java.util.Objects;

public class Attribute {

    private String name;
    private ResultType type;
    private AttributeValue value;

    public Attribute(String name, ResultType type) {
        this.name = name;
        this.type = type;
    }

    public ResultType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public AttributeValue getValue() {
        return value;
    }

    public void setValue(AttributeValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Attribute attribute = (Attribute) object;
        return Objects.equals(getName(), attribute.getName()) && getType() == attribute.getType() && Objects.equals(getValue(), attribute.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getValue());
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
