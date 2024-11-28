package connections.query.resultAttribute;

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
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
