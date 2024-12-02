package connections;

import connections.query.queryAttribute.QueryAttribute;
import connections.query.resultAttribute.Attribute;

import java.util.HashSet;
import java.util.List;

public interface DbConnection {
    public void create(String query, List<QueryAttribute> queryAttributes);
    public void delete(String query, List<QueryAttribute> queryAttributes);
    public List<HashSet<Attribute>> query(String query, List<QueryAttribute> queryAttributes, List<Attribute> outputAttributes);
}
