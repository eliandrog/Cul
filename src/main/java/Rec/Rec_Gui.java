package Rec;

import javax.swing.*;
import java.awt.*;

public class Rec_Gui extends JFrame{



    JTextField usernameTf;
    JTextField passwordTf;
    JTextField passwordCf;
    JPanel centerPnl;
    JButton loginBtn,registerBtn;
    Box loginBox;
    JLabel errorMessage, passCf;


    public  Rec_Gui(){


        usernameTf=new JTextField(28);
        passwordTf=new JTextField(28);
        passwordCf=new JTextField(25);
        passCf=new JLabel("Confirm Password:");
        errorMessage=new JLabel();
        loginBtn=new JButton("Login");
        registerBtn=new JButton("Register");
        JLabel usernameLbl=new JLabel("Username: ");
        JLabel passwordLbl=new JLabel("Password: ");
//        usernameLbl.setLabelFor(usernameTf);
//        passwordLbl.setLabelFor(passwordTf);

        //create and populate panel

        centerPnl= new JPanel();
        //BoxLayout layout=new BoxLayout(centerPnl);

        centerPnl.add(usernameLbl);
        centerPnl.add(usernameTf);
        centerPnl.add(passwordLbl);
        centerPnl.add(passwordTf);
        centerPnl.add(loginBtn);
        centerPnl.add(registerBtn);


        centerPnl.setMaximumSize(new Dimension(450,11000));
        centerPnl.setMinimumSize(new Dimension(450,1000));
        loginBox=new Box(BoxLayout.Y_AXIS);
        loginBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        loginBox.add(Box.createVerticalGlue());
        loginBox.add(centerPnl);
        loginBox.add(Box.createVerticalGlue());


        pack();

        add(loginBox);


        //add(centerPnl);
        setSize(800,800);
        centerPnl.setBorder(BorderFactory.createLineBorder(Color.RED));
        loginBox.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        //buttonlisteners
        ButtonListeners listeners=new ButtonListeners(this);
        loginBtn.addActionListener(listeners);
        registerBtn.addActionListener(listeners);

        //table existance values


    }



}

