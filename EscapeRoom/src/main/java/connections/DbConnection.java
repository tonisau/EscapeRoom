package connections;

import connections.attribute.Attribute;
import connections.attribute.queryAttribute.QueryAttribute;

import java.util.HashSet;
import java.util.List;

public interface DbConnection {
    public void create(String query, List<QueryAttribute> queryAttributes);

    public void delete(String query, List<QueryAttribute> queryAttributes);

    public List<HashSet<Attribute>> query(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes);

    public <T> void createWithReflection(String query, T object);

    public void createWithGenerics(String query, List<Attribute> queryAttributes);
}

