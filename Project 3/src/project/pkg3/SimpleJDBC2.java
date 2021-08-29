// Very basic JDBC example showing loading of JDBC driver, establishing connection
// creating a statement, executing a simple SQL query, and displaying the results.
// This version uses the implicit driver loading using a MySQLDataSource object
import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class SimpleJDBC2 {
  public static void main(String[] args)
    throws SQLException, ClassNotFoundException {
	  System.out.println("Output from SimpleJDBC2");
	  java.util.Date date = new java.util.Date();
	  System.out.println(date);  System.out.println();		 
	
	//without a properties file include these statements
	MysqlDataSource dataSource = new MysqlDataSource();
	dataSource.setUser("root");
	dataSource.setPassword("rootMAC1$");
	dataSource.setURL("jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC");
	  
	//establish a connection to the dataSource - the database
	Connection connection = dataSource.getConnection();
    System.out.println("Database connected");
	 
	 DatabaseMetaData dbMetaData = connection.getMetaData();
	 System.out.println("JDBC Driver name " + dbMetaData.getDriverName() );
	 System.out.println("JDBC Driver version " + dbMetaData.getDriverVersion());
	 System.out.println("Driver Major version " +dbMetaData.getDriverMajorVersion());
	 System.out.println("Driver Minor version " +dbMetaData.getDriverMinorVersion() );

    // Create a statement
	Statement statement = connection.createStatement();
	
    // Execute a statement
    ResultSet resultSet = statement.executeQuery ("select bikename,cost,mileage from bikes");

    // Iterate through the result set and print the returned results
    while (resultSet.next())
      System.out.println(resultSet.getString("bikename") + "         \t" +
        resultSet.getString("cost") + "         \t" + resultSet.getString("mileage"));
		//the following print statement works exactly the same  
      //System.out.println(resultSet.getString(1) + "         \t" +
      //  resultSet.getString(2) + "         \t" + resultSet.getString(3));
     System.out.println();  System.out.println();
    // Close the connection
    connection.close();
  }
}

