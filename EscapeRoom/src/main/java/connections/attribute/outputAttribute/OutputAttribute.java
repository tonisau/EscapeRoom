package connections.attribute.outputAttribute;

import java.util.Objects;

@SuppressWarnings("rawtypes")
public class OutputAttribute {

    private final String name;
    private final OutputType type;
    private AttributeValue value;

    public OutputAttribute(String name, OutputType type) {
        this.name = name;
        this.type = type;
    }

    public OutputType getType() {
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
        OutputAttribute attribute = (OutputAttribute) object;
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
