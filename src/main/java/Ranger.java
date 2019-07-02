import WildlifeDao.SqlDao;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranger implements SqlDao {
    private static final String url = "jdbc:postgresql://localhost:5432/wildlife_tracker";
    private static  final String user = "likad";
    private  static final String password = "prismas";
    private int id;
    private String name;
    private String passcode;
    private long phonenumber;
    private int no_of_animals;
    private int no_of_endangeredanimals;
    private Date creation;
    public static HashMap<Integer,Ranger> allRangers=new HashMap<>();

    private static String rangerinsert="INSERT INTO \"Ranger\"(name,password,number,no_of_animals,no_of_endangeredanimals,creationdate) VALUES(?,?,?,?,?,?)";
    private static String selectAllRangers="SELECT * FROM \"Ranger\"";
    private static String rangerExists="SELECT name FROM \"Ranger\"";
    private static String rangerId="SELECT id FROM \"Ranger\" WHERE name=?";
    private static String reqSession="SELECT id FROM \"Ranger\" WHERE name=?";
    private static String updateRanger="UPDATE \"Ranger\" SET name=?, password=?, number=? WHERE id=?";
    public static ArrayList<Animals> totalRangers=new ArrayList<>();
    public static HashMap<Integer,HashMap<String,Object>> selectAllNew=new HashMap<>();
    private static Connection conn = null;
    private static int rangerid=100;
    Ranger(String name, String passcode,long phonenumber){
    this.id=rangerid;
    this.name=name;
    this.passcode=passcode;
 this.phonenumber=phonenumber;
//    this.no_of_animals=no_of_animals;
//    this.no_of_endangeredanimals=no_of_endangeredanimals;
    java.util.Date ty=new java.util.Date();
    java.sql.Date enter=new java.sql.Date(ty.getTime());
    this.creation=enter;
    rangerid=rangerid+1;
    this.add();

}
public void removezero(Long i){
    String thenumber=Long.toString(i);
    char[] chars=thenumber.toCharArray();
    char[] finn=new char[chars.length-1];
    for(int j=0,k=0;j<chars.length && k<finn.length;j++,k++){
        if(chars[0]==0){
            for(int l=1;l<chars.length;l++){
                chars[l]=chars[l];
            }
           continue;
        }else{
            finn[k]=chars[j];
        }
    }
    String string=new String(finn);
    String str=new String(chars);
    long finished=Long.parseLong(string);
    long finish=Long.parseLong(str);
    this.phonenumber=finish;
    System.out.println(finished);
    System.out.println(chars);
}
    public static long updateremovezero(Long i){
        String thenumber=Long.toString(i);
        char[] chars=thenumber.toCharArray();
        char[] finn=new char[chars.length-1];
        for(int j=0,k=0;j<chars.length && k<finn.length;j++,k++){
            if(chars[0]==0){
                for(int l=1;l<chars.length;l++){
                    chars[l]=chars[l];
                }
                continue;
            }else{
                finn[k]=chars[j];
            }
        }
        String string=new String(finn);
        String str=new String(chars);
        long finished=Long.parseLong(string);
        long finish=Long.parseLong(str);
        return finish;
    }
    public void add(){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(rangerinsert);


            stmt.setString(1,this.name);
            stmt.setString(2,this.passcode);
            stmt.setLong(3,this.phonenumber);
            stmt.setInt(4,0);
            stmt.setInt(5,0);
            stmt.setDate(6,this.creation);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void selectAll(){

        try {
            conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(selectAllRangers);
            ResultSet st=stmt.executeQuery();
            ResultSetMetaData metadata=st.getMetaData();
            int columncount=metadata.getColumnCount();
            int count=1;
            while (st.next()) {
                ArrayList<Object> yes=new ArrayList<>();
                HashMap<String,Object> yesman=new HashMap<>();
                int id= st.getInt("id");
                String name= st.getString("name");
                String pass=st.getString("password");
                Long number=st.getLong("number");
                int animalsnumber=st.getInt("no_of_animals");
                int endangerednumber=st.getInt("no_of_endangeredanimals");
                Date creation=st.getDate("creationdate");
                yes.add(id);
                yes.add(name);
                yes.add(pass);
                yes.add(number);
                yes.add(animalsnumber);
                yes.add(endangerednumber);
                yes.add(creation);
                yesman.put("rangerid",id);
                yesman.put("name",name);
                yesman.put("password",pass);
                yesman.put("number",number);
                yesman.put("animalsnumber",animalsnumber);
                yesman.put("endangerednumber",creation);
                yesman.put("creation",animalsnumber);
                selectAllNew.put(count,yesman);
                count++;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    };
public static int reqSession(String name){
    int theid=0;
    try {
        conn = DriverManager.getConnection(url, user, password);

        PreparedStatement stmt = conn.prepareStatement(reqSession);
        stmt.setString(1,name);
        ResultSet st=stmt.executeQuery();
        ResultSetMetaData metadata=st.getMetaData();
        int columncount=metadata.getColumnCount();
        int count=1;
        while (st.next()) {
            int id=st.getInt("id");
            theid=id;

        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return theid;
}
 public void update(){

 }
    public void updateRanger(int id,String name,String password,Long number){
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(updateRanger);

            stmt.setString(1,name);
            stmt.setString(2,password);
            stmt.setLong(3,number);
            stmt.setInt(4,id);


            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }
    public static int getRangerId(String name){
        boolean verify=false;
        int getId=0;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(rangerId);
            stmt.setString(1,name);
            ResultSet st=stmt.executeQuery();

            while (st.next()){


                        int id=st.getInt("id");
                        getId=id;




            }



            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return getId;
    }
    public static boolean usernameExists(String name){
        boolean verify=false;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(rangerExists);
            ResultSet st=stmt.executeQuery();

            while (st.next()){
                String passcode=st.getString("name");

                if(passcode.equals(name)){
                   verify=true;
                    return verify;
                }else {

                }

            }



            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
return verify;
    }
    public static boolean passwordTrue(String name,String passcode){
        boolean verify=false;
        try {
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(selectAllRangers);
            ResultSet st=stmt.executeQuery();

            while (st.next()){
                String thename=st.getString("name");
                String pass=st.getString("password");

                if(passcode.equals(pass) && thename.equals(name)){
                    verify=true;

                }else {

                }

            }



            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verify;
    }
    public void delete(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getPasscode() {
        return passcode;
    }



    public long getPhonenumber() {
        return phonenumber;
    }



    public int getNo_of_animals() {
        return no_of_animals;
    }


    public int getNo_of_endangeredanimals() {
        return no_of_endangeredanimals;
    }



    public Date getCreation() {
        return creation;
    }


}
