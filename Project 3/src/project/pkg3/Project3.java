
package project.pkg3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Project3 implements ActionListener{

    //GUI Variables
    JButton connectDB;
    JButton clearSQL;
    JButton executeSQL;
    JButton clearResult;
    
    JFrame frame;
    JPanel cPanel;
    JPanel ePanel;
    JPanel sPanel;
    
    JTextField jdbcDriver;
    JTextField dbURL;
    JTextField usernameField;
    JPasswordField passwordField;
    JTextArea enterCommand;
    JTextArea execWindow;
    
    JLabel jdbcLabel;
    JLabel databaseLabel;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel connectionLabel;
    JLabel resultLabel;
    
    JComboBox<String> jdbcOption;
    JComboBox<String> urlOption;
    
    String[] jdbcDriverString = {"com.mysql.cj.jdbc.Driver", "oracle.jdbc.driver.OracleDriver",
        "com.ibm.db2.jdbc.netDB2Driver", "com.jdbc.ocbc.jdbcOdbcDriver"};
    String[] databaseURLString = {"jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC", 
        "jdbc:mysql://localhost:3306/project3?useTimezone=true&serverTimezone=UTC"};
    
    //Connection Variables
    String driver;
    String databaseURL;
    String password;
    String username;
    
    //Other Variables
    int i, x;
    String command;
    Connection connection;
    String temp;

    public static void main(String[] args) {
        Project3 project = new Project3();
        project.GUISetup();
    }
    
    public void GUISetup () {
        
        //Buttons
        connectDB = new JButton();
        clearSQL = new JButton();
        executeSQL = new JButton();
        clearResult = new JButton();
        
        cPanel = new JPanel();
        cPanel.setMinimumSize(new Dimension(600,250));
        cPanel.setPreferredSize(new Dimension(600,250));
        cPanel.setBorder(BorderFactory.createTitledBorder("Enter Database Information"));
        cPanel.setLayout(new GridLayout(4,2, 10, 10));
        
        ePanel = new JPanel();
        ePanel.setMinimumSize(new Dimension(400,250));
        ePanel.setPreferredSize(new Dimension(400,250));
        ePanel.setBorder(BorderFactory.createTitledBorder("Enter SQL Command"));
        ePanel.setLayout(new GridLayout(1,1,100, 100));
        
        sPanel = new JPanel();
        sPanel.setMinimumSize(new Dimension(800,350));
        sPanel.setPreferredSize(new Dimension(800,350));
        sPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        sPanel.setBackground(Color.lightGray);
        sPanel.setLayout(new GridBagLayout());
        
        //Frame instance
        frame = new JFrame("Project 3");
        frame.setSize(1000,600);
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(cPanel, layout.CENTER);
        frame.add(ePanel, layout.EAST);
        frame.add(sPanel, layout.SOUTH);
        
        //Field Configuration
        //C panel
        jdbcLabel = new JLabel("JDBC Driver", JLabel.LEFT);
        cPanel.add(jdbcLabel);
        jdbcOption = new JComboBox<String>(jdbcDriverString);
        cPanel.add(jdbcOption);
        
        databaseLabel = new JLabel("Database URL", JLabel.LEFT);
        cPanel.add(databaseLabel);
        urlOption = new JComboBox<String>(databaseURLString);
        cPanel.add(urlOption);
        
        usernameLabel = new JLabel("Username", JLabel.LEFT);
        cPanel.add(usernameLabel);
        usernameField = new JTextField(50);
        cPanel.add(usernameField);
        
        passwordLabel = new JLabel("Password", JLabel.LEFT);
        cPanel.add(passwordLabel);
        passwordField = new JPasswordField(50);
        cPanel.add(passwordField);
        
        //E Panel
        enterCommand = new JTextArea(); 
        enterCommand.setLineWrap(true);
        ePanel.add(enterCommand);
                
        //S Panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = .1;
        connectionLabel = new JLabel("Connection", JLabel.CENTER);
        connectionLabel.setMaximumSize(new Dimension(400,50));
        connectionLabel.setMinimumSize(new Dimension(400,50));
        connectionLabel.setPreferredSize(new Dimension(400,50));
        sPanel.add(connectionLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = .1;
        c.weighty = .1;
        connectDB.addActionListener(this);
        connectDB.setText("Connect to Database");
        sPanel.add(connectDB, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        clearSQL.addActionListener(this);
        clearSQL.setText("Clear SQL Command");
        sPanel.add(clearSQL, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 0;
        executeSQL.addActionListener(this);
        executeSQL.setText("Execute SQL Command");
        sPanel.add(executeSQL, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.insets = new Insets(0, 0, 5, 0);
        resultLabel = new JLabel("            SQL Execution Result Window", JLabel.LEFT);
        sPanel.add(resultLabel, c);
        
        c.fill = GridBagConstraints.VERTICAL;    
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 5;
        c.gridheight = 3;
        c.insets = new Insets(0, 0, 0, 0);
        execWindow = new JTextArea();
        execWindow.setEditable(false);
        JScrollPane scroll = new JScrollPane (execWindow, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(900, 200));
        execWindow.setFont(new Font("monospaced", Font.PLAIN, 12));
        sPanel.add(scroll, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(2, 35, 2, 200);
        clearResult.addActionListener(this);
        clearResult.setText("Clear Result Window");
        sPanel.add(clearResult, c);
        
        
        frame.setVisible(true);
    }
    
    public void setupConnection() throws SQLException, ClassNotFoundException {
	System.out.println("Output from SimpleJDBC");
	java.util.Date date = new java.util.Date();
	System.out.println(date);  System.out.println();
	
        // Load the JDBC driver
        Class.forName(driver);
        System.out.println("Driver loaded");

        // Establish a connection
        connection = DriverManager.getConnection(databaseURL, username, password);
        System.out.println("Database connected");
        connectionLabel.setText("Connected to " + databaseURL);

        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("JDBC Driver name " + dbMetaData.getDriverName() );
        System.out.println("JDBC Driver version " + dbMetaData.getDriverVersion());
        System.out.println("Driver Major version " +dbMetaData.getDriverMajorVersion());
        System.out.println("Driver Minor version " +dbMetaData.getDriverMinorVersion() );

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a statement
//        ResultSet resultSet = statement.executeQuery ("select bikename,cost,mileage from bikes");

        // Iterate through the result set and print the returned results
//        while (resultSet.next())
//          System.out.println(resultSet.getString("bikename") + "         \t" +
//            resultSet.getString("cost") + "         \t" + resultSet.getString("mileage") + "\n");
        System.out.println();
        System.out.println();
        // Close the connection
//        connection.close();

    }
    
    public void executeCommand(){
        try {
            command = enterCommand.getText();
            System.out.println(command);
            
            // Create a statement
            Statement statement1 = connection.createStatement();
            
            // Execute a statement
            System.out.println(command.split(" ")[0]);
            if (command.split(" ")[0].equals("select")){
                ResultSet resultSet1 = statement1.executeQuery (command);
                ResultSetMetaData data = resultSet1.getMetaData();

                // Iterate through the result set and print the returned results
                int rowInt = 0;
                while (resultSet1.next()){
                    for (int j = 0; j < data.getColumnCount(); j++){
                        if ((resultSet1.getRow() == 1 ) && (rowInt == 0)){
                            for (int k = 0; k < data.getColumnCount(); k++){
                                temp = temp.format("%-20s", data.getColumnName(k+1));
                                execWindow.append(temp);
//                                execWindow.append(data.getColumnName(k+1));
                            }
                            rowInt = 1;
                            execWindow.append("\n");
                        }
//                        System.out.println(resultSet1.getString(data.getColumnName(j+1)));
                        temp = temp.format("%-20s", resultSet1.getString(data.getColumnName(j+1)));
                        execWindow.append(temp);
                        System.out.println(temp);
//                        execWindow.append(resultSet1.getString(data.getColumnName(j+1)));
                    }
                    execWindow.append("\n");
                    execWindow.setCaretPosition(execWindow.getDocument().getLength());
                }
                execWindow.append("\n");
            }
            else {
                statement1.executeUpdate(command);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Project3.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ((SQLException)ex).getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clearResult){
            execWindow.setText("");
        }
        
        if (ae.getSource() == clearSQL){
            enterCommand.setText("");
        }
         
        if (ae.getSource() == connectDB){
            i = jdbcOption.getSelectedIndex();
            driver = jdbcDriverString[i];
            i = urlOption.getSelectedIndex();
            databaseURL = databaseURLString[i];
            if(usernameField.getText().equals(""))
                System.out.println("No username entered");
            else
                username = usernameField.getText();
            if(passwordField.getText().equals(""))
                System.out.println("No password entered");
            else 
                password = passwordField.getText();
            try {
                setupConnection();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ((SQLException)ex).getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ((ClassNotFoundException)ex).getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
          
        if (ae.getSource() == executeSQL){
            executeCommand();
        }
    }
}

     
