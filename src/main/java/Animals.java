
import WildlifeDao.SqlDao;
import WildlifeDao.WildlifeaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animals implements SqlDao {
    public static final String url = "jdbc:postgresql://ec2-23-21-160-38.compute-1.amazonaws.com/diblbe28bluam";
    public static final String user = "zxbeaauddplwmn";
    public static  final String password = "0508f0d46b5f3daa8e4fa1e3d77c25f555df40722eb2fca8a6a0f11b6d9ac4cf";
    public static HashMap<Integer,Animals> allAnimals=new HashMap<>();
    public static HashMap<Integer,Animals> all_Endangered_Animals=new HashMap<>();
    public static HashMap<Integer,HashMap<String,Object>> selectAllNewAnimals=new HashMap<>();
    public static HashMap<Integer,HashMap<String,Object>> selectAllNewEndangaredAnimals=new HashMap<>();
    public static ArrayList<String> animalnamesnormal=new ArrayList<>();
    public static ArrayList<String> animalnamesendangered=new ArrayList<>();
    private static String normalanimal="INSERT INTO normalanimal(name,creation,times_sighted) VALUES(?,?,?)";
    private static String normalsightings="INSERT INTO normalsightings(name,timessighted,created_at) VALUES(?,?,?)";
    private static String endangeredsightings="INSERT INTO endangeredsightings(name,timessighted,created_at) VALUES(?,?,?)";

    private static String selectAllNormalAnimal="SELECT * FROM normalanimal";

    private int timessightedNormal=0;
    private int timessightedEndangered=0;
    private static String selectAllEndangeredAnimals="SELECT * FROM endangeredsightings";
    private static String animalExists="SELECT name FROM normalanimal";
    private static String selectanimalName="SELECT name FROM normalanimal WHERE id=?";
    private static String selectEndangeredName="SELECT name FROM endangeredsightings WHERE id=?";
    private static String endangeredAnimalExists="SELECT name FROM endangeredsightings";
    //Array list of objects normal animals
    public static ArrayList<Animals> totalAnimals=new ArrayList<>();
    //Array list of objects endangered animals
    public static ArrayList<Animals> totalEndangeredAnimals=new ArrayList<>();
    private static Connection conn = null;
    private static int normalid=200;
    private int personalid;

    private static int endangeredid=100;
    private int age;
    private String name;
    private java.sql.Date creation;
    private String en_age;
    private String health;

    Animals(String name){

        this.name=name;

        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        this.creation=enter;
        totalAnimals.add(this);
        normalid=normalid+1;
        this.personalid=normalid;
        this.addNormalanimal();

    }
    Animals(int age, String name){

        this.name=name;

        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        this.creation=enter;
        totalEndangeredAnimals.add(this);
        endangeredid=endangeredid+1;
        this.personalid=endangeredid;
        this.addEndangeredAnimal();

    }


    public void addNormalanimal(){

        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(normalanimal);
            PreparedStatement stmts = conn.prepareStatement(normalsightings);


                    stmt.setString(1,this.name);
                    stmt.setDate(2,this.creation);
                    stmt.setInt(3,0);
                     stmts.setString(1,this.name);
                     stmts.setInt(2,0);
                     stmts.setDate(3,this.creation);
                    stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    };
    public static boolean animalExists(String name){
        boolean verify=false;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(animalExists);
            ResultSet st=stmt.executeQuery();

            while (st.next()){
                String thename=st.getString("name");

                if(thename.equals(name)){
                    verify=true;
                    break;
                }else {

                }

            }



            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verify;
    }
    public static boolean endangeredanimalExists(String name){
        boolean verify=false;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(endangeredAnimalExists);
            ResultSet st=stmt.executeQuery();

            while (st.next()){
                String thename=st.getString("name");

                if(thename.equals(name)){
                    verify=true;
                    break;
                }else{

                }
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verify;
    }
    public void addEndangeredAnimal(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(endangeredsightings);

            stmt.setString(1,this.name);
            stmt.setInt(2,0);
            stmt.setDate(3,this.creation);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    };
    public static void selectAllAnimal(){

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAllNormalAnimal);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=1;
            while (st.next()) {
                ArrayList<Object> yes=new ArrayList<>();
                HashMap<String,Object> yesman=new HashMap<>();

                int id= st.getInt("id");
                String name= st.getString("name");
                Date creation=st.getDate("creation");
                String sighted= st.getString("times_sighted");
                yes.add(id);
                yes.add(name);
                yes.add(creation);
                yes.add(sighted);
                //An array list of normal animals names
                animalnamesnormal.add(name);
                yesman.put("normalid",id);
                yesman.put("name",name);
                yesman.put("creation",creation);
                yesman.put("sighted",sighted);
                //Hashmap of all details
                selectAllNewAnimals.put(count,yesman);
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    };
    public static String getNormalAnimalName(int theId){
        String animalname="";

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectanimalName);
            stmt.setInt(1,theId);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            while (st.next()) {
                String name= st.getString("name");
                animalname=name;
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animalname;

    }
    public static String getEndangeredName(int theId){
        String animalname="";

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectEndangeredName);
            stmt.setInt(1,theId);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            while (st.next()) {
                String name= st.getString("name");
                animalname=name;
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return animalname;

    }
    public static void selectAllEndangeredAnimal(){

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAllEndangeredAnimals);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=1;
            while (st.next()) {
                ArrayList<Object> yes=new ArrayList<>();
                HashMap<String,Object> yesman=new HashMap<>();
                String name= st.getString("name");
                int sighted= st.getInt("timessighted");
                Date creation=st.getDate("created_at");
                int id=st.getInt("id");


                yes.add(name);
                yes.add(sighted);
                yes.add(creation);
                //an arraylist of endangered animals names
                animalnamesendangered.add(name);
                yesman.put("name",name);
                yesman.put("sighted",sighted);
                yesman.put("creation",creation);
                yesman.put("en_id",id);

                //A hashmap of all details
                selectAllNewEndangaredAnimals.put(count,yesman);
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    };
    public int getAge(){
        return  this.age;
    }
    public String getName(){
        return this.name;
    }
    public Date getCreation(){
        return this.creation;
    }
    public String getEn_age(){
        return this.en_age;
    }
    public String getHealth(){
        return this.health;
    }
    public int getNormalid(){
        return  this.personalid;
    }
    public int getEndangeredid(){
        return  this.personalid;
    }
    public int getTimessightedNormal(){
        return this.timessightedNormal;
    }
    public int getTimessightedEndangered(){
        return this.timessightedEndangered;
    }
    public void add(){

    }
    @Override
    public void update(){

    }
    public void delete(){

    }


};


