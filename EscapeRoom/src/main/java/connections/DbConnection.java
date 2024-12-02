package connections;

import connections.attribute.outputAttribute.OutputAttribute;
import connections.attribute.queryAttribute.QueryAttribute;

import java.util.HashSet;
import java.util.List;

public interface DbConnection {
    public void create(String query, List<QueryAttribute> queryAttributes);
    public void delete(String query, List<QueryAttribute> queryAttributes);
    public List<HashSet<OutputAttribute>> query(String query, List<QueryAttribute> queryAttributes, List<OutputAttribute> outputAttributes);
}
