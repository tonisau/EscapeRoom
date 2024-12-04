package DAO.interfaces;

import java.util.List;

public interface DAO<T> {
    List<T> showData();
    void delete(Integer id);
}
