/*  AnySelect.java
 *  
 *  Modified code based on AnySelect.java furnished by Professor Anderson
 * 
 */
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

            // use ResultSetMetaData to display column names and data
            ResultSetMetaData rsmd = resultSet.getMetaData();

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
