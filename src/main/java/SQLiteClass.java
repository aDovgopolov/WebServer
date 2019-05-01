import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteClass {
    public static Connection conn;
    public static Statement stat;
    public static ResultSet rs;

    public static void Conn() throws ClassNotFoundException, SQLException, NamingException {

        System.out.println("before load");
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("1");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC",
                "root", "Buggati");
        System.out.println("2");
    }

    public static void addName(String name) throws ClassNotFoundException, SQLException {
        try {
            Conn();
            stat = conn.createStatement();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO names (name) VALUES (?)");
            statement.setString(1, name);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            stat.close();
            CloseDB();
        }
    }

    public static JSONArray getAllNames() throws ClassNotFoundException, SQLException, NamingException
    {
        ArrayList<String> ldaps = new ArrayList<String>();
        ArrayList<String> sername = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();

        HashMap<String, ArrayList<String>> hmap = new HashMap();

        Conn();
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from test.rep_emp");

        JSONArray ja = new JSONArray();

        while (rs.next()) {
            JSONObject jsonToReturn0 = new JSONObject();
            jsonToReturn0.put("ldap", rs.getString("LDAP_LOGIN"));
            jsonToReturn0.put("sername", rs.getString("REP_FAM"));
            jsonToReturn0.put("name", rs.getString("REP_NAME"));
            ja.put(jsonToReturn0);
           // ldaps.add(rs.getString("LDAP_LOGIN"));
            //sername.add(rs.getString("REP_FAM"));
            //names.add(rs.getString("REP_NAME"));
            System.out.println(rs.getString("LDAP_LOGIN"));
        }

        hmap.put("ldap", ldaps);
        hmap.put("sername", sername);
        hmap.put("names", names);

        rs.close();
        stat.close();
        CloseDB();

        return ja;
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
    }
}