package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

    public ManagersController(){
        this.setupEndpoints();
    }

    private void setupEndpoints(){

        get("/managers", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            List<Employee> managers = DBHelper.getAll(Manager.class);
            model.put("template", "models/templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "models/templates/managers/create.vtl");
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/managers", (req, res) ->{
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int budget = Integer.parseInt(req.queryParams("budget"));
            Manager manager = new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        get("/managers/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(id, Manager.class);
            model.put("manager", manager);
            model.put("template", "models/templates/managers/show.vtl");
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/:id/delete", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(id, Manager.class);
            DBHelper.delete(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        get("/managers/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(id, Manager.class);
            model.put("manager", manager);
            model.put("template", "models/templates/managers/edit.vtl");
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

    }

}
