import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

import static org.junit.Assert.*;

public class AnimalsTest {
    private static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
    private static final String user = "likad";
    private static final String password = "prismas";
    private static String normalanimal="INSERT INTO normalanimal(id,name,creation,times_sighted) VALUES(?,?,?,?)";
    private static String endangeredanimal="INSERT INTO endangeredanimals(id,name,health,age,creation,times_sighted) VALUES(?,?,?,?,?,?)";
    private String insert="INSERT INTO normalanimal(id,name,creation) VALUES(?,?,?)";
    Connection conn = null;

    @Before
    public void setup(){


        try {
            conn = DriverManager.getConnection(url, user, password);


            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    public void enteranimal(){

        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        Animals one=new Animals(12,"Yeet");

        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(normalanimal);
            stmt.setInt(1,one.getNormalid());
            stmt.setString(2,one.getName());
            stmt.setDate(3,one.getCreation());
            stmt.setInt(4,one.getTimessightedNormal());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String pattern = "dd-mm-yyyy";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(ty);
        System.out.println(date);
        System.out.println(ty);

    }
    @Test
    public void enterendangeredanimal(){

        java.util.Date ty=new java.util.Date();
        java.sql.Date enter=new java.sql.Date(ty.getTime());
        Animals one=new Animals(12,"Yeet","Young","Fine",enter);
        Animals one1=new Animals(12,"Yeetuu","Old","Fine",enter);
        System.out.println("One1 id is: "+one1.getEndangeredid());
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(endangeredanimal);
            stmt.setInt(1,one1.getEndangeredid());
            stmt.setString(2,one1.getName());
            stmt.setString(3,one1.getHealth());
            stmt.setString(4,one1.getEn_age());
            stmt.setDate(5,one1.getCreation());
            stmt.setInt(6,one.getTimessightedEndangered());
            stmt.executeUpdate();
System.out.println("Successfuly inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String pattern = "dd-mm-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(ty);
        System.out.println(date);
        System.out.println(ty);

    }
@Test
    public void getnormalanimals(){
        System.out.println(Animals.selectAllNewAnimals);
}

}
