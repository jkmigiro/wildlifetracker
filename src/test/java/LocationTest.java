import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LocationTest {
    private final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
    private final String user = "likad";
    private final String password = "prismas";
    private static String thelocation="INSERT INTO location(id,place,timesvisited) VALUES(?,?,?)";
    Connection conn = null;
    @Test
    public void add() {
        Location location=new Location(1,"Near the river",0);
        Location location2=new Location(2,"At the rocks",0);
        Location location3=new Location(3,"In the forest",0);
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(thelocation);

            stmt.setInt(1,location3.getId());
            stmt.setString(2,location3.getPlace());
            stmt.setInt(3, location3.getTimesvisited());

            stmt.executeUpdate();
            System.out.println("Succesfully added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}
