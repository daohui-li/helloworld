package db.dao;

import db.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao
{
    void save(Employee employee) throws SQLException;
    Employee findByPrimaryKey(long id) throws SQLException;
    List<Employee> getAll() throws SQLException;
    void update(Employee employee) throws SQLException;
    void delete(Employee employee) throws SQLException;
}
