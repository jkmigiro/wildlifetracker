import WildlifeDao.SqlDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Location implements SqlDao {
    private static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker";
    private static final String user = "likad";
    private static  final String password = "prismas";
    private static Connection conn = null;
    private int id;
    private String place;
    private int timesvisited;
    public static HashMap<Integer, HashMap<String,Object>> selectAllNew=new HashMap<>();
    private static String thelocation="INSERT INTO location(id,place,times_visited) VALUES(?,?,?)";
    private static String selectAll="SELECT * FROM location";
    private static String selectLocationName="SELECT place FROM location WHERE id=?";

    Location(int id,String place,int timesvisited){
        this.id=id;
        this.place=place;
        this.timesvisited=timesvisited;
    }
    public void add(){

        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(thelocation);

            stmt.setInt(1,this.id);
            stmt.setString(2,this.place);
            stmt.setInt(3,this.timesvisited);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }





    };
    public void update(){

    }
    public void delete(){

    }
public static void select(){


        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAll);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=1;
            while (st.next()) {
                ArrayList<Object> yes=new ArrayList<>();
                HashMap<String,Object> yesman=new HashMap<>();
                int id= st.getInt("id");
                String place= st.getString("place");
                int timesvisited=st.getInt("timesvisited");
                System.out.println("id"+id+" place "+place+" times visited "+timesvisited);
                yes.add(id);
                yes.add(place);
                yes.add(timesvisited);
                yesman.put("locationid",id);
                yesman.put("place",place);
                yesman.put("timesvisited",timesvisited);

                selectAllNew.put(count,yesman);
                count++;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


}
    public static String getLocationName(int theId){
String locationname="";

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectLocationName);
            stmt.setInt(1,theId);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            while (st.next()) {
                String place= st.getString("place");
                locationname=place;
                break;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return locationname;

    }
    //The getter methods
    public int getId(){
        return this.id;
    }
    public String getPlace(){
        return this.place;
    }
    public int getTimesvisited(){
        return this.timesvisited;
    }
}
