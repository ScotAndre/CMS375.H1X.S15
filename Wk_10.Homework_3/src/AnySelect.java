import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 *  AnySelect.java
 *
 *  Scot Andre                                              sandre@rollins.edu
 *  CMS375 Spring 2015
 *  Professor Anderson
 *  31 March 2015
 *
 *  Assignment instructions:
 *  Write a Java application that uses JDBC to display sections and semester
 *  for a course.
 *  First your program should prompt the user for the user name and password.
 *  Then your program should read the course names from the database, display
 *  all the course names, and prompt the user for which course they want the
 *  information.
 *
 *  You can build a GUI for this program if you want. In that case, the user
 *  would select the course name from a JComboBox.
 *
 *  Example output if the user chooses "Intro to C++":
 *
 *     Course Name             Section       Semester
 *     Intro to C++                  2       Fall
 *                                   1       Fall
 *
 *  Note that the course name is outputed only once, even if the course has
 *  multiple sections.
 *
 *  Deliverables:
 *    Your Java source code (zipped) and a report that answers these questions:
 *
 *    1. Explain the roles that each of these components play in JDBC:
 *       a. JDBC Driver
 *       b. Connection
 *       c. Session
 *       d. Statement
 *       e. ResultSet
 *       f. ResultSet MetaData
 *
 *    2. When should you use the 'executeUpdate' method versus the
 *       'executeQuery' method? In other words, what is the difference between
 *       these two statements?
 */
public class AnySelect {
    private Connection connection;
    private String dbUser = "";
    private String dbPassword = "";

    public AnySelect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }// end constructor

    public String execute(String sqlSelectStatement){
        String data = "";

        connect();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlSelectStatement);
            System.out.println("\nHere is the resultSet:");
            System.out.println(resultSet);

            // use ResultSetMetaData to display column names and data
            ResultSetMetaData rsmd = resultSet.getMetaData();
            System.out.println("\n\nHere is the rsmd: ");
            System.out.println(rsmd);

            // create header from ResultSetMetaData
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                data += rsmd.getColumnName(i) + "\t";
            }
            data += "\n";

            // retrieve returned data from ResultSet using ResultSetMetaData
            while(resultSet.next()){
                for(int col = 1; col <= rsmd.getColumnCount(); col++){
                    switch(rsmd.getColumnType(col)){
                        case Types.NUMERIC:
                            data += resultSet.getInt(col) + "\t";
                            break;
                        case Types.VARCHAR:
                        case Types.CHAR:
                            data += resultSet.getString(col) + "\t";
                            break;
                        case Types.TIMESTAMP:
                            data += resultSet.getDate(col) + "\t";
                    }// end switch
                }// end column loop
                data += "\n";
            }// end loop through records
            statement.close();
            connection.close();
        } catch (SQLException sqle){
            System.out.println("SQL Exception: " + sqle);
            sqle.printStackTrace();
        }
        return data;
    }// end execute() method

    public void connect() {
        String host = "oai.rollins.edu";
        String port = "1521";
        String SID = "cms375";

        // get user credentials if necessary
        if (dbUser.equals("") && dbPassword.equals("")) {
            JPanel credentialPanel = new JPanel();
            JLabel userNameLabel = new JLabel("User Name: ");
            JTextField userName = new JTextField(10);

            JLabel passwordLabel = new JLabel("Password: ");
            JPasswordField password = new JPasswordField(10);

            credentialPanel.add(userNameLabel);
            credentialPanel.add(userName);
            credentialPanel.add(passwordLabel);
            credentialPanel.add(password);
            String[] options = {"OK", "Cancel"};

            int option = JOptionPane.showOptionDialog(null, credentialPanel,
                    "Enter Database Credentials", JOptionPane.NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (option == 0) {
                // choose OK button
                char[] userPassword = password.getPassword();
                dbUser = userName.getText();
                dbPassword = new String(userPassword);
            } else {
                // choose Cancel Button
                System.out.println("Exiting");
                System.exit(1);
            }
        }

        // connect to the database
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@"
                    + host + ":" + port + ":" + SID, dbUser, dbPassword);
            System.out.println("Successfully connected to the database.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}// end class
