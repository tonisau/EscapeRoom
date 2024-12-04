package DAO.interfaces;

import java.util.List;

public interface DAO<T> {
    List<T> getData();
    void delete(Integer id);
}
