import javax.swing.*;
import java.awt.*;

public class Rec_Gui extends JFrame {



    public JTextField usernameTf,passwordTf;
    public JPanel centerPnl;
    static JButton loginBtn;
    public Box loginBox;


    public Rec_Gui(){
        usernameTf=new JTextField(20);
        passwordTf=new JTextField(20);
        loginBtn=new JButton("login");
        JLabel usernameLbl=new JLabel("Username: ",JLabel.TRAILING);
        JLabel passwordLbl=new JLabel("Password: ",JLabel.TRAILING);
//        usernameLbl.setLabelFor(usernameTf);
//        passwordLbl.setLabelFor(passwordTf);

        //create and populate panel

        centerPnl= new JPanel();
        BoxLayout layout=new BoxLayout(centerPnl,BoxLayout.Y_AXIS);

        centerPnl.add(usernameTf);
        centerPnl.add(passwordTf);
        centerPnl.add(loginBtn);


        centerPnl.setMaximumSize(new Dimension(350,2500));
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
    }







    public static void main(String[] args) {
        Runnable r=new Runnable() {
            @Override
            public void run() {
                Rec_Gui frame=new Rec_Gui();
                frame.setVisible(true);
            }
        };
        r.run();


    }


}

