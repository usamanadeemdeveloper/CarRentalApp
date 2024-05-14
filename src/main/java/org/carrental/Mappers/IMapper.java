package org.carrental.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IMapper<T> {
    List<T> resultToList(ResultSet rs) throws SQLException;
    T resultSetToObject(ResultSet rs) throws SQLException;
}
