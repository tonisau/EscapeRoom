package DAO.implementations;

import connections.DbConnection;
import connections.attribute.Attribute;

import java.util.HashSet;
import java.util.List;

public class DBConnectionMock implements DbConnection {

    List<Attribute> queryAttributes;

    @Override
    public Boolean create(String query, List<Attribute> queryAttributes) {
        this.queryAttributes = queryAttributes;
        return null;
    }

    @Override
    public void delete(String query, List<Attribute> queryAttributes) {

    }

    @Override
    public List<HashSet<Attribute>> query(String query, List<Attribute> queryAttributes, List<Attribute> outputAttributes) {
        return List.of();
    }
}
