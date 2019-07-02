import WildlifeDao.WildlifeaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Sightings implements WildlifeaDao {
    private static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker";
    private static final String user = "likad";
    private static final String password = "prismas";
    private int id;
    private String location_name;
    private Date creation;
    private int ranger_id;
    private int animal_id;
    private String health;
    private int location_id;
    private String age;
    private String endangeredanimal_name;
    private int en_id;
    private String normalanimal_name;
    private static int totalsightings;
    private static int selectrowAffected;
    public static HashMap<Integer,Sightings> allSightings=new HashMap<>();
    public static HashMap<Integer,HashMap<String,Object>> selectAllNew=new HashMap<>();
    public static ArrayList<Sightings> totalSightings=new ArrayList<>();
    public static Connection conn = null;
    private static String enternormalanimal="INSERT INTO sightings(location,creation,ranger_id,animal_id,location_name,animal_name) VALUES(?,?,?,?,?,?)";
    private static String enterendangeredanimal="INSERT INTO sightings(location,creation,ranger_id,location_name,endangeredanimal_name,endangaredanimal_id) VALUES(?,?,?,?,?,?)";
    private static String updatelocation="UPDATE location SET timesvisited = timesvisited + 1 WHERE id=?";
    private static String updateNormalAnimal="UPDATE normalanimal SET times_sighted = times_sighted + 1 WHERE id=?";
    private static String updateEndangeredAnimal="UPDATE endangeredsightings SET timessighted = timessighted + 1 WHERE id=?";
    private static String updateRangerAnimals="UPDATE \"Ranger\" SET no_of_animals=no_of_animals +1  WHERE id=?";
    private static String endangeredanimal="INSERT INTO endangeredanimals(name,health,age,creation) VALUES(?,?,?,?)";
    private static String updateRangerEndangered="UPDATE \"Ranger\" SET no_of_endangeredanimals=no_of_endangeredanimals +1  WHERE id=?";
    private static String selectAll="SELECT * FROM sightings";
    private static String selectAnimal="SELECT * FROM normalsighting";
    private static String selectEndangered="SELECT * FROM endangeredsightings";
    private static int sightingsId=1;
    private static String orderbyrow1="SELECT * FROM sightings ORDER BY id";
    private static String orderbyrow2="SELECT * FROM sightings ORDER BY location";
    private static String orderbyrow3="SELECT * FROM sightings ORDER BY creation";
    private static String orderbyrow4="SELECT * FROM sightings ORDER BY ranger_id";
    private static String orderbyrow5="SELECT * FROM sightings ORDER BY animal_id";
    private static String orderbyrow6="SELECT * FROM sightings ORDER BY endangeredanimal_id";
  //This is a normal animal sighting
    Sightings(int location_id,int ranger_id,int animal_id,String location_name,String normalanimal_name) {

        this.location_id=location_id;
        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        this.creation=enter;
        this.ranger_id=ranger_id;
        this.animal_id=animal_id;
        this.location_name=location_name;
        this.normalanimal_name=normalanimal_name;

        totalSightings.add(this);
        allSightings.put(totalSightings.size(),this);
        sightingsId=sightingsId+1;
        this.addSightingfor_normalanimal();
        this.updateNormalAnimal();
    }
    //This is an endangered animal sighting
    Sightings(int location_id,String age,int ranger_id,String health,String location_name,String endangeredanimal_name,int en_id){

        this.location_id=location_id;
        this.ranger_id=ranger_id;
        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        this.creation=enter;
        this.endangeredanimal_name=endangeredanimal_name;
        this.location_name=location_name;
        this.en_id=en_id;
        this.health=health;
        this.age=age;

        totalSightings.add(this);
        allSightings.put(totalSightings.size(),this);
        sightingsId=sightingsId+1;
        this.addSightingfor_endangeredanimal();
        this.updateEndangered();
    }
    public void addRanger(){

    };
    public void addSightingfor_normalanimal(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(enternormalanimal);
            PreparedStatement updt = conn.prepareStatement(updateNormalAnimal);

            stmt.setInt(1,this.location_id);
            stmt.setDate(2,this.creation);
            stmt.setInt(3,this.ranger_id);
            stmt.setInt(4,this.animal_id);
            stmt.setString(5,this.location_name);
            stmt.setString(6,this.normalanimal_name);

            updt.setInt(1,this.animal_id);
            stmt.executeUpdate();
            updt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    };
    public void addNormalanimal(){

    };
    public void addEndangeredAnimal(){



    };
    public void addLocation(){

    };
    public void addSightingfor_endangeredanimal(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(enterendangeredanimal);
            PreparedStatement updt = conn.prepareStatement(updateEndangeredAnimal);

            PreparedStatement getpt= conn.prepareStatement(endangeredanimal);



//            private static String enterendangeredanimal="INSERT INTO sightings(location,creation,ranger_id,location_name,endangeredanimal_name,endangaredanimal_id) VALUES(?,?,?,?,?,?)";
//            private static String endangeredanimal="INSERT INTO endangeredanimals(name,health,age,creation) VALUES(?,?,?,?)";
            stmt.setInt(1,this.location_id);
            stmt.setDate(2,this.creation);
            stmt.setInt(3,this.ranger_id);
            stmt.setString(4,this.location_name);
            stmt.setString(5,this.endangeredanimal_name);
            stmt.setInt(6,this.en_id);

            updt.setInt(1,this.en_id);

            getpt.setString(1,this.endangeredanimal_name);
            getpt.setString(2,this.health);
            getpt.setString(3,this.age);
            getpt.setDate(4,this.creation);

            stmt.executeUpdate();
            getpt.executeUpdate();
            updt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateNormalAnimal(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(updatelocation);
            PreparedStatement rngt = conn.prepareStatement(updateRangerAnimals);

            stmt.setInt(1,this.location_id);
            rngt.setInt(1,this.ranger_id);
            rngt.executeUpdate();
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    public void updateEndangered(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(updatelocation);
            PreparedStatement rngt_en = conn.prepareStatement(updateRangerEndangered);

            stmt.setInt(1,this.location_id);
            rngt_en.setInt(1,this.ranger_id);
            stmt.executeUpdate();
            rngt_en.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    public static void selectAll(){

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAll);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=0;
            while (st.next()) {
                ArrayList<Object> yes=new ArrayList<>();
                HashMap<String,Object> yesman=new HashMap<>();
                int id= st.getInt("id");
                int locationid= st.getInt("location");
                Date creation=st.getDate("creation");
                int ranger_id=st.getInt("ranger_id");
                int animal_id=st.getInt("animal_id");
                String locationname=st.getString("location_name");
                String animalname=st.getString("animal_name");
                String en_name=st.getString("endangeredanimal_name");
                int endangeredanimal_id=st.getInt("endangeredanimal_id");
                yesman.put("id",id);
                yesman.put("locationname",locationname);
                yesman.put("locationid",locationid);
                yesman.put("animalname",animalname);
                yesman.put("animalid",animal_id);
                yesman.put("rangerid",ranger_id);
                yesman.put("enname",en_name);
                yesman.put("enid",endangeredanimal_id);
                yesman.put("creation",creation);
                yes.add(endangeredanimal_id);
                selectAllNew.put(count,yesman);
                count++;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    };
    public void Orderbyrow1(){

    }
    //These are the getter methods
public int getId(){
        return this.id;

}
public int getLocation(){
        return this.location_id;
}
public Date getCreation(){
        return this.creation;
}
public int getRanger_id(){
        return this.ranger_id;
}
public int getAnimal_id(){
        return this.animal_id;
}
public String getLocation_name(){
        return this.location_name;
}
public String getEndangeredanimal_id(){
        return this.endangeredanimal_name;
}
    }




