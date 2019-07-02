import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class RangerTest {
    private final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker_test";
    private final String user = "likad";
    private final String password = "prismas";
    Connection conn = null;
    private static String rangerinsert="INSERT INTO \"Ranger\"(name,password,number,no_of_animals,no_of_endangeredanimals,creationdate) VALUES(?,?,?,?,?,?)";
    private static String rangerExists="SELECT name,password FROM \"Ranger\"";
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() {
        Ranger ranger=new Ranger("Hera","Zeus",75665555);
        Ranger ranger1=new Ranger("Hera","Zeus",75665555);
        Ranger ranger2=new Ranger("Hera","Zeus",75665555);
        Ranger ranger3=new Ranger("Hera","Zeus",75665555);

        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(rangerinsert);


            stmt.setString(1,ranger3.getName());
            stmt.setString(2,ranger3.getPasscode());
            stmt.setLong(3,ranger3.getPhonenumber());
            stmt.setInt(4,ranger3.getNo_of_animals());
            stmt.setInt(5,ranger3.getNo_of_endangeredanimals());
            stmt.setDate(6,ranger3.getCreation());
            System.out.println("Successfully inserted");
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    String name="passwod";
@Test
public void Verify(){

    boolean verify=false;
    try {
        conn = DriverManager.getConnection(url, user, password);
        PreparedStatement stmt = conn.prepareStatement(rangerExists);
        ResultSet st=stmt.executeQuery();

        while (st.next()){
            String passcode=st.getString("password");

            if(passcode.equals(name)){
                verify=true;

            }else {

            }

        }



        stmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    System.out.println(verify);

}

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }


}
