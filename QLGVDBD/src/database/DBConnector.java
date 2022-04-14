package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

public class DBConnector {
	private Connection con = null;
	private String usrName, dbName, hostname, pwd;
	private static DBConnector db = null;
	
	synchronized public static DBConnector getInstance() throws SQLException{
		if(db == null)
			db = new DBConnector("localhost", "qlbd", "root", "");
		return db;
	}
	
	private DBConnector(String pHostname,
			String pDbName, String pUsrName, String pPwd) throws SQLException{
		dbName = pDbName;
		hostname = pHostname;
		usrName = pUsrName;
		pwd = pPwd;
		String strCon = "jdbc:mysql://" + hostname + "/" + dbName +
				"?useUnicode=true&characterEncoding=utf-8";
		Properties prop = new Properties();
		prop.put("user", usrName);
		prop.put("password", pwd);
		Driver driver;
		driver = new Driver();
		con = driver.connect(strCon, prop);

	}
	
	public Connection getConnection(){
		return con;
	}
	
	public void close(){
		if(con != null){
			try {
				con.close();
				System.out.println("Closed connection");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
