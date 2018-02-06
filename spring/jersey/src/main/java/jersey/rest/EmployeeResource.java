package jersey.rest;

import db.dao.EmployeeDao;
import db.model.Employee;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    private EmployeeDao employeeDao;

    @GET
    public Response getEmployees() throws SQLException {
        List<Employee> employees = employeeDao.getAll();
        return Response.ok().entity(employees).build();
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getEmployee(@PathParam("id") Long id) throws SQLException {
        Employee employee = employeeDao.findByPrimaryKey(id);
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(employee).build();
    }

    @PUT
    public Response updateEmployee(Employee employee) throws SQLException {
        employeeDao.update(employee);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    public void createEmployee(Employee employee) throws SQLException {
        employeeDao.save(employee);
    }

    @DELETE
    @Path("{id:[0-9]+}")
    public void deleteEmployee(@PathParam("id") Long id) throws SQLException {
        Employee employee = employeeDao.findByPrimaryKey(id);
        if (employee == null) {
            throw new SQLException("TODO: not existing object - " + id);
        }
        employeeDao.delete(employee);
    }
}
