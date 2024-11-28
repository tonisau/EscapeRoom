package DAO.interfaces;

import java.util.List;

public interface DAO<T> {

    void add(T t);
    List<T> showData();

}
