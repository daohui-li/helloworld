package db.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="employee")
@NamedQueries( {
        @NamedQuery(name = Employee.GET_ALL_EMPLOYEES_NQ, query ="SELECT e FROM Employee e")
} )
public class Employee {
    public static final String GET_ALL_EMPLOYEES_NQ = "NQ.Get.Employees";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private long id;

    @Column(name = "employee_name")
    @Size(min=1, max=10)
    private String name;

    public Employee() {
        this(null);
    }

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("id=%d. name=%s", id, name);
    }
}
