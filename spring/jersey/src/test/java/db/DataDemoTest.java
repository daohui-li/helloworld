package db;

import db.config.DBConfig;
import db.dao.EmployeeDao;
import db.model.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.TransactionSystemException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Ignore // TODO: use H2 database (instead of mysql)
public class DataDemoTest {
    AnnotationConfigApplicationContext context;
    DataSource dataSource;
    EmployeeDao employeeDao;

    List<Employee> employees = new ArrayList<>();

    @Before
    public void setup() {
        context = new AnnotationConfigApplicationContext();
        context.register(DBConfig.class);
        context.scan("db.dao");
        context.refresh();
        dataSource = context.getBean(DataSource.class);
        employeeDao = (EmployeeDao) context.getBean("myEmployDao");

        employees.clear();
    }

    @Test
    public void create() throws SQLException {
        Employee employee = new Employee("E123");
        employeeDao.save(employee);
        System.out.println("Employee:" + employee);

        assertTrue(employee.getId() != null);
        employees.add(employee);
    }

    @Test(expected = TransactionSystemException.class)
    public void createNameTooLong() throws SQLException {
        Employee employee = new Employee("Employee123");
        employeeDao.save(employee);
        System.out.println("Employee:" + employee);

        assertTrue(employee.getId() != null);
        employees.add(employee);

        assertTrue("Shall not reach to here", false);
    }

    @Test
    public void testGetAll() throws SQLException {
        create();

        List<Employee> employees = employeeDao.getAll();
        System.out.println("number of employees is " + employees.size());

        assertTrue(!employees.isEmpty());
    }

    @After
    public void cleanup() {
        for (Employee employee : employees) {
            try {
                System.out.println("deleting " + employee);
                employeeDao.delete(employee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
