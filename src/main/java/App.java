import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;


import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class App {
    public static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker";
    public static final String user = "likad";
    public static  final String password = "prismas";
    public static int theId;
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String args[]){
        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        get("/sighting", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            return new ModelAndView(model, "welcome.css");
        }, new HandlebarsTemplateEngine());
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "welcoming.hbs");
        }, new HandlebarsTemplateEngine());
        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        //the ranger logs in
        post("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String username=req.queryParams("username");
            String password=req.queryParams("password");
            boolean wrong=true;
            if(Ranger.passwordTrue(username,password)){
                theId=Ranger.getRangerId(username);
                System.out.println("True username"+username);
                System.out.println("True password"+password);
                System.out.println("The Id is: "+theId);
                System.out.println(Ranger.getRangerId(username));
                wrong=false;
                model.put("id",theId);
                return new ModelAndView(model, "navigate.hbs");
            }else {
                System.out.println("False username"+username);
                System.out.println("False username"+password);
                model.put("wrong",wrong);
                return new ModelAndView(model, "login.hbs");
            }
        }, new HandlebarsTemplateEngine());
//        post("/verification", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            String username=req.queryParams("username");
//            String password=req.queryParams("password");
//            if(Ranger.usernameExists(username)){
//                req.session().attribute("user");
//                return new ModelAndView(model, "sightingsnormalanimal.hbs");
//            }else {
//                return new ModelAndView(model, "login.hbs");
//            }
//        }, new HandlebarsTemplateEngine());
        get("/signup", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "enterranger.hbs");
        }, new HandlebarsTemplateEngine());
        //verify if user is signed up
        post("/verification", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String username=req.queryParams("rangerusername");
            String password=req.queryParams("rangerpassword");
            Long phonenumber=Long.parseLong(req.queryParams("rangerphonenumber"));
            boolean exists=true;
            if(Ranger.usernameExists(username)){
                req.session().attribute("rangerusername");
                model.put("exists",Ranger.usernameExists(username));
                return new ModelAndView(model, "enterranger.hbs");
            }else {
                exists=!exists;
                Ranger ranger=new Ranger(username,password,phonenumber);
                System.out.println(username);
                System.out.println(password);
                System.out.println(ranger.getId());
                System.out.println(ranger.getPhonenumber());

                return new ModelAndView(model, "success.hbs");
            }
        }, new HandlebarsTemplateEngine());
        //This is a new normal animal
        get("/newnormalanimal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            Animals.selectAllAnimal();
            System.out.println("Animal names normal: "+Animals.animalnamesnormal);
            System.out.println("Animal names select all: "+Animals.selectAllNewAnimals);
            System.out.println("Animal names total: "+Animals.totalAnimals);
            model.put("animals",Animals.selectAllNewAnimals);
            return new ModelAndView(model, "newnormalanimal.hbs");
        }, new HandlebarsTemplateEngine());
        //Add a new normal animal
        post("/newnormalanimal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name=req.queryParams("name");
            boolean exists=true;
            if(Animals.animalExists(name)){
                model.put("exists",exists);
                return new ModelAndView(model, "newnormalanimal.hbs");
            }else{
                exists=!exists;
                System.out.println("Does not exist: "+Animals.animalExists(name));
                Animals animals=new Animals(name);
                return new ModelAndView(model, "successanimal.hbs");
            }

        }, new HandlebarsTemplateEngine());
        //This is a new endangered animal animal
        get("/newendangered", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animals.selectAllEndangeredAnimal();
            model.put("animals",Animals.selectAllNewEndangaredAnimals);
            return new ModelAndView(model, "newendangered.hbs");
        }, new HandlebarsTemplateEngine());
        //Add a new normal animal
        post("/newendangered", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name=req.queryParams("name");

            boolean exists=true;
            if(Animals.endangeredanimalExists(name)){
                return new ModelAndView(model, "newendangered.hbs");
            }else{
                exists=!exists;
                System.out.println("Does not exist: "+Animals.endangeredanimalExists(name));
                model.put("exists",exists);
                Animals animals=new Animals(1,name);
                return new ModelAndView(model, "successanimal.hbs");
            }

        }, new HandlebarsTemplateEngine());
        //Sighting of a normal animal
        get("/sightingsnormal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            //display normal animals
            Animals.selectAllAnimal();
            model.put("animals",Animals.selectAllNewAnimals);
            System.out.println(Animals.selectAllNewAnimals);
            //For displaying locations
            Location.select();
            model.put("location",Location.selectAllNew);
            System.out.println(Location.selectAllNew);
            //For displaying rangers
            Ranger.selectAll();
            model.put("rangers",Ranger.selectAllNew);
            System.out.println(Ranger.selectAllNew);
            return new ModelAndView(model, "sightingsnormalanimal.hbs");
        }, new HandlebarsTemplateEngine());



        //Getting data of a new normal animal
        post("/sightingsnormalanimal", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int rangerid=Integer.parseInt(req.queryParams("rangers"));
            int locationid=Integer.parseInt(req.queryParams("location"));
            int normalid=Integer.parseInt(req.queryParams("normal"));

            String locationname=Location.getLocationName(locationid);
            String normalname=Animals.getNormalAnimalName(normalid);
//            int locationid=Integer.parseInt(req.params(":locationid"));
//            int animalid=Integer.parseInt(req.params(":animalid"));


            System.out.println("Location name: "+locationname);
            System.out.println("Location id: "+locationid);
            System.out.println("Animal name: "+normalname);
            System.out.println("Animal id: "+normalid);
            System.out.println("Ranger id: "+rangerid);
            Sightings normalanimal=new Sightings(locationid,rangerid,normalid,locationname,normalname);

            return new ModelAndView(model, "success_sighting.hbs");
        }, new HandlebarsTemplateEngine());


        //sighting of a new endangared
        get("/sightingsendangered", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animals.selectAllEndangeredAnimal();
            model.put("animals",Animals.selectAllNewEndangaredAnimals);
            System.out.println(Animals.selectAllNewEndangaredAnimals);
            //For displaying locations
            Location.select();
            model.put("location",Location.selectAllNew);
            System.out.println(Location.selectAllNew);
            //For displaying rangers
            Ranger.selectAll();
            model.put("rangers",Ranger.selectAllNew);
            System.out.println(Ranger.selectAllNew);
            return new ModelAndView(model, "sightingsendangered.hbs");
        }, new HandlebarsTemplateEngine());
        //Post endangared
        post("/sightingsendangered", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int rangerid=Integer.parseInt(req.queryParams("rangers"));
            int locationid=Integer.parseInt(req.queryParams("location"));
            int en_id=Integer.parseInt(req.queryParams("endangered"));
            String health=req.queryParams("health");
            String age=req.queryParams("age");
            String locationname=Location.getLocationName(locationid);
            String normalname=Animals.getEndangeredName(en_id);

            Sightings sightings=new Sightings(locationid,age,rangerid,health,locationname,normalname,en_id);
            return new ModelAndView(model, "success_sighting.hbs");
        }, new HandlebarsTemplateEngine());
        //Viewing all normal animals
        get("/viewnormalanimals", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animals.selectAllAnimal();
            model.put("animals",Animals.selectAllNewAnimals);
            return new ModelAndView(model, "viewnormalanimals.hbs");
        }, new HandlebarsTemplateEngine());
        get("/viewendangered", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Animals.selectAllEndangeredAnimal();
            model.put("animals",Animals.selectAllNewEndangaredAnimals);
            return new ModelAndView(model, "viewendangeredanimals.hbs");
        }, new HandlebarsTemplateEngine());
        get("/viewsightings", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Sightings.selectAll();
            System.out.println(Sightings.selectAllNew);
            model.put("sightings",Sightings.selectAllNew);
            return new ModelAndView(model, "viewsightings.hbs");
        }, new HandlebarsTemplateEngine());

//        post("/sightings", (req, res) -> {


//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "sightingsendangeredanimal.hbs");
//        }, new HandlebarsTemplateEngine());
    }
}
