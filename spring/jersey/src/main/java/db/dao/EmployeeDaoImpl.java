package db.dao;

import db.model.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository("myEmployDao")  // this is a repository bean
@Transactional  // ask Spring framework to provide a method used in the methods
public class EmployeeDaoImpl implements EmployeeDao {
    @PersistenceContext  // injects an entityManager
    private EntityManager entityManager;

    @Override
    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public Employee findByPrimaryKey(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public void delete(Employee employee) throws SQLException {
        if (!entityManager.contains(employee)) {
            employee = findByPrimaryKey(employee.getId());
        }
        entityManager.remove(employee);
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        return entityManager.createNamedQuery(Employee.GET_ALL_EMPLOYEES_NQ, Employee.class).getResultList();
    }

    @Override
    public void update(Employee employee) throws SQLException {
        if (employee.getId() == null || findByPrimaryKey(employee.getId()) == null) {
            throw new SQLException("invalid input");
        }
        entityManager.merge(employee);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
