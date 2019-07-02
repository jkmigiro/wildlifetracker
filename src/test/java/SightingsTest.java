import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SightingsTest {
    private static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
    private static final String user = "likad";
    private static final String password = "prismas";
    private static String enternormal="INSERT INTO sightings(id,location,creation,ranger_id,animal_id,location_name) VALUES(?,?,?,?,?,?)";
    private static String selectAll="SELECT * FROM sightings";
    public static Connection conn = null;
    public static HashMap<Integer,ArrayList<Object>> selectAllNew=new HashMap<>();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addRanger() {
    }

    @Test
    public void addSightingfor_normalanimal() {
        java.util.Date ty=new java.util.Date();
        java.sql.Date thedate=new java.sql.Date(ty.getTime());
        Sightings sightings=new Sightings(1,1,thedate,1,201,"Near the river");

        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(enternormal);

            stmt.setInt(1,sightings.getId());
            stmt.setInt(2,sightings.getLocation());
            stmt.setDate(3,sightings.getCreation());
            stmt.setInt(4,sightings.getRanger_id());
            stmt.setInt(5,sightings.getAnimal_id());
            stmt.setString(6,sightings.getLocation_name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addNormalanimal() {
    }

    @Test
    public void addEndangeredAnimal() {
    }

    @Test
    public void addLocation() {
    }

    @Test
    public void addSightingfor_endangeredanimal() {
    }

    @Test
    public void update() {
    }

    @Test
    public void selectAll() {
        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAll);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=1;
            while (st.next() && count<metadata.getColumnCount()) {
                ArrayList<Object> yes=new ArrayList<>();
                int id= st.getInt("id");
                String location= st.getString("location");
                Date creation=st.getDate("creation");
                int ranger_id=st.getInt("ranger_id");
                int animal_id=st.getInt("animal_id");
                int endangeredanimal_id=st.getInt("endangeredanimal_id");
                yes.add(id);
                yes.add(location);
                yes.add(creation);
                yes.add(ranger_id);
                yes.add(animal_id);
                yes.add(endangeredanimal_id);
                selectAllNew.put(count,yes);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(selectAllNew);
    }

    @Test
    public void orderbyrow1() {
    }
}
