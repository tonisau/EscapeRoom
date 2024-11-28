package connections.query.resultAttribute;

public class Attribute {

    private String name;
    private ResultType type;

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
}
