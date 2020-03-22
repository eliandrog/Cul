package Rec;

import Rec.Rec_Gui;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonListeners implements ActionListener {
    private int exists=0;
    private String prgramMessageFdback;
    private Rec_Gui gui;
    private boolean rgtbuttonPressed;



    ButtonListeners(Rec_Gui theGui){

        gui=theGui;
    }

    public void actionPerformed(ActionEvent e) {


        //loggin button actions
        if (e.getSource()== gui.loginBtn ){
            System.out.println("Login-in User.......");
            //System.out.println(checkTableExistance("users"));
            if (!checkTableExistance("users")){
                System.out.println("im in the loop");
            }else{
                checkLogindb(gui.usernameTf.getText(),gui.passwordTf.getText());
            }
        }

        //registration button actions
        if (e.getSource()==gui.registerBtn){


            //creating new user form
            if (!rgtbuttonPressed){
                if (gui.errorMessage.isShowing()){gui.centerPnl.remove(gui.errorMessage);}
                gui.centerPnl.remove(gui.loginBtn);
                gui.centerPnl.remove(gui.registerBtn);
                gui.centerPnl.add(gui.passCf);
                gui.centerPnl.add(gui.passwordCf);
                rgtbuttonPressed=true;
                System.out.println("Button added");
                gui.registerBtn.setLabel("Register User");
                gui.centerPnl.add(gui.loginBtn);
                gui.centerPnl.add(gui.registerBtn);
                gui.centerPnl.add(gui.errorMessage);
                gui.centerPnl.revalidate();
                gui.repaint();

                //once pressed one timeF
            }else{

                //check if user is valid
                if (gui.usernameTf.getText()==null || gui.usernameTf.getText().trim().isEmpty()) {
                    setPrgramMessageFdback("Try input username again!");

                }else {
                    //checking both passs are the same
                    if (gui.passwordTf.getText().equals(gui.passwordCf.getText())){
                        System.out.println("user creation underway....");
                        createUser(gui.usernameTf.getText(),gui.passwordTf.getText());
                        System.out.println("User has been created");

                        //if user is created reset to home settings
                        gui.centerPnl.remove(gui.passwordCf);
                        gui.centerPnl.remove(gui.passCf);

                        gui.registerBtn.setText("Register");
                        rgtbuttonPressed=false;
                        //gui.centerPnl.revalidate();
                        gui.repaint();


                    }else{
                        System.out.println("");
                        setPrgramMessageFdback("Password is not the same");
    //                    gui.errorMessage.setText("Password is not the same");
    //                    gui.errorMessage.setForeground(Color.red);
    //                    gui.centerPnl.add(gui.errorMessage);
    //                    gui.revalidate();
                    }
                }

            }

        }
    }

    public void createUser(String username,String password){


        try{
//            //Specify to the DriverManager which JDBC drivers to try to make Connections with
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            //make a connection with a particular db
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/users" +
                                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
                            , "root", "root");
//
//            //updating table
            System.out.println("Updating users input..........");
            Statement stmt = conn.createStatement();
//
//            //user previosu existance
//            String sql=String.format("select if( exists(select username from user where username='%s'),1,0);",username);
//            ResultSet usernameResult =stmt.executeQuery(sql);
//            usernameResult.next();

            //DOOOOOOOOOOOOOOOOO
            //CHECK FOR USERNAME EXISTANCE IN TABLE BEFORE INSERTING 1=true

            int userNExist=checkUsernameInDB(username);
            if (userNExist==0){
                //sending hasshed pass to db
                String sql = String.format("INSERT INTO user(username , pass) " +
                        "values('%s','%s')", username, BCrypt.hashpw(password, BCrypt.gensalt(12)));
                stmt.executeUpdate(sql);

                setPrgramMessageFdback(String.format("User %s has been created", username));

            }else if ((userNExist==1)){
                System.out.println("User %s already exists");
                setPrgramMessageFdback(String.format("User %s already exists", username));

            }

            conn.close();


        } catch (Exception s){
            s.printStackTrace();
            setPrgramMessageFdback("Error in creating user");

        }

    }

    public boolean checkLogindb(String username, String userpass) {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/users" +
                                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
                            , "root", "root");

            //hashing password using bcrypt
            //String password = BCrypt.hashpw(userpass, BCrypt.gensalt(12));
//            System.out.println(password);
//            System.out.println("match = "+BCrypt.checkpw("d", password)); //checking for the hashing

            //checking the db for particular usernames existance
            //making the query
            String userNameQry=String.format("Select if( exists(select * from user where username='%s'),1,0);",username);
            Statement userValidationQry=conn.createStatement();
            ResultSet rs=userValidationQry.executeQuery(userNameQry);
            rs.next();

            //checking hashed password against db
            int r=Integer.parseInt(rs.getString(1));    //gets the result from db 1 user exist 0 it doesnt
            System.out.println("username ="+r);

            //if user exist
            if (r==1){
                //checking for the pass hash
                System.out.println("checking password....");
                String passQry=String.format("Select pass from user where username='%s';",username);
                Statement passValidationQry=conn.createStatement();
                ResultSet rsPass=passValidationQry.executeQuery(passQry);
                rsPass.next();
                System.out.println("hashed pass (db) = "+rsPass.getString(1)+"\nactual hash =");
                boolean correctPass=BCrypt.checkpw(userpass, rsPass.getString(1));
                prgramMessageFdback="user exists";
                System.out.println(prgramMessageFdback);

                //password correctness
                if (correctPass){

                    prgramMessageFdback="Correct password entered";
                    System.out.println(prgramMessageFdback);return true;
                }else{
                    prgramMessageFdback="Wrong password entered, try again ";
                    System.out.println(prgramMessageFdback); return false;
                }

            }else if (r==0){
                prgramMessageFdback="User does not exist, try again";
                System.out.println(prgramMessageFdback);
                return false;

            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public int checkUsernameInDB(String username) throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/users" +
                                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
                            , "root", "root");


            String userNameQry=String.format("Select if( exists(select * from user where username='%s'),1,0);",username);
            Statement userValidationQry=conn.createStatement();
            ResultSet rs=userValidationQry.executeQuery(userNameQry);
            rs.next();
            //conn.close();

            return Integer.parseInt(rs.getString(1));
        } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }

        return -1;
    }


    public boolean checkTableExistance(String dbname){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/users" +
                            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
                            ,"root","root");
            ResultSet resultset=conn.getMetaData().getCatalogs();
            while(resultset.next()){
                //System.out.println(resultset.getString(1));
                if(resultset.getString(1).toUpperCase().equals(dbname.toUpperCase())){
                    conn.close();
                    return true;
                }
            }





        }catch (Exception e){
            e.printStackTrace();
        }

        //when it gets here db doesnt exist
        return false;
    }

    public void setPrgramMessageFdback(String message){



        if (!gui.errorMessage.isShowing() ){
            gui.errorMessage.setText(message);
            gui.errorMessage.setForeground(Color.red);
            gui.centerPnl.add(gui.errorMessage);
            gui.revalidate();


        }else{
            gui.errorMessage.setText(message);
            gui.errorMessage.setForeground(Color.red);
            gui.revalidate();
        }

    }

    public static void main(String[] args) {

    }

}
