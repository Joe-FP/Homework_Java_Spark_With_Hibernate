package controllers;

import db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;

import static spark.Spark.get;

public class Main {

    public static void main(String[] args) {
        EmployeesController employeesController = new EmployeesController();
        ManagersController managersController = new ManagersController();
        EngineersController engineersController = new EngineersController();
        Seeds.seedData();

        get("/", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "models/templates/main/index.vtl");
            return new ModelAndView(model, "models/templates/layout.vtl");
        }, new VelocityTemplateEngine());

    }

}`
