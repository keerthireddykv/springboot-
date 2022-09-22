package springexample2.springboot;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
	private static String empid = "EMPID";
	private static String firstname = "FIRSTNAME";
	private static String lastname = "LASTNAME";
	private static String dob = "DOB";
	private static String location = "LOCATION";
	private static String empadd = "EMPADD";
	@GetMapping("/data")
	 
	public String getEmployeeDetails(Integer empID) throws SQLException, IOException{	
		FileInputStream stream = new FileInputStream("Resources//config.properties");
		Properties prop = new Properties();
		prop.load(stream);
		String URL = prop.getProperty("URL");
	    String UNAME = prop.getProperty("UNAME");
	    String PASS = prop.getProperty("PASS");
		String query = "select empid, firstname,lastname,dob,location,empadd from employeee where empid ?";
		String userData = "";		
		System.out.println(URL);
		System.out.println(UNAME);
		System.out.println(PASS);
		
		try {
            System.out.println("empID"  + empID);
			Class.forName("com.mysql.cj.jdbc.Driver");//load and register
			Connection con = DriverManager.getConnection(URL,UNAME,PASS);
			CallableStatement statement = con.prepareCall("{call empidd(?)}");
    		statement.setInt(1, empID); 
			ResultSet rs = statement.executeQuery();   
			while(rs.next())
			{
				userData = rs.getInt(empid)+":"+rs.getString(firstname)+""+rs.getString(lastname)+":"+rs.getString(dob)+":"+rs.getString(location)+":"+rs.getString(empadd);
				System.out.println(userData);				
			}	
			statement.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return userData;
	}
}