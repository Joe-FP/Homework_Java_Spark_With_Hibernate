package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {

    public EngineersController(){
        this.setupEndpoints();
    }

    private void setupEndpoints(){

        get("/engineers", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            List<Employee> engineers = DBHelper.getAll(Engineer.class);
            model.put("template", "models/templates/engineers/index.vtl");
            model.put("engineers", engineers);
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "models/templates/engineers/create.vtl");
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/engineers", (req, res) ->{
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Engineer engineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null;
        }, new VelocityTemplateEngine());

    }

}
