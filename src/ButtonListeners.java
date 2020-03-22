import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class ButtonListeners implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== Rec_Gui.loginBtn ){



        }
    }


    public boolean checkIfDbExist(String dbName){

        Connection connection;
        try{
            //Specify to the DriverManager which JDBC drivers to try to make Connections with
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            //make a connection with a particular db
            connection = DriverManager.getConnection("jdbc:derby:testdb;");

            ResultSet resultSet = connection.getMetaData().getCatalogs();

            //iterate each catalog(Database names) in the ResultSet
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                if(databaseName.equals("recipes")){
                    return true;
                }
            }
            resultSet.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        return false;


    }


}
