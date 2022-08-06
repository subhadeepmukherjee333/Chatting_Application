


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;
import java.io.*;



public class Server implements ActionListener{

    JTextField text;
    JPanel body;
    static DataOutputStream dataout;

    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();



    Server(){

        f.setLayout(null);

        JPanel head = new JPanel();
        head.setBackground(new Color(10,151,162));
        head.setBounds(0,0,450,65);
        head.setLayout(null);
        f.add(head);
        
        f.setSize(450, 650);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);
        

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25,25);
        head.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
              System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/img1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50,50);
        head.add(profile);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30,30);
        head.add(video);


        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35,30);
        head.add(phone);


        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(415, 20, 10,25);
        head.add(morevert);

        JLabel name = new JLabel("Lily");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        name.setBounds(110,15,100,18);
        head.add(name);

        JLabel status = new JLabel("Active now");
        status.setForeground(new Color(199,199,199));
        status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        status.setBounds(110,35,100,18);
        head.add(status);



        body = new JPanel();
        body.setBounds(5, 65, 440, 530);
        f.add(body);

        text = new JTextField();
        text.setBounds(5, 600, 310, 47);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,600,123,45);
        send.setBackground(new Color(10,151,162));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.setFocusable(false);
        send.addActionListener(this);
        f.add(send);

        f.setUndecorated(true);
        f.setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        try{


            String massage = text.getText();
        
            
            // JLabel p11 = new JLabel(massage);
            
            JPanel p = formatLabel(massage);
            // JPanel p = new JPanel();
            // p.add(p11);
            
            body.setLayout(new BorderLayout());
        
            JPanel right = new JPanel(new BorderLayout());
            right.add(p, BorderLayout.LINE_END);
            // right.setBackground(new Color(225,228,171));
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
        
            body.add(vertical, BorderLayout.PAGE_START);
        
            dataout.writeUTF(massage);
        
            text.setText("");
        
            f.repaint();
            f.invalidate();
            f.validate();
        }catch(Exception ae){
            ae.printStackTrace();
        }
            
    }

    public static JPanel formatLabel(String massage){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel massage1 = new JLabel("<html> <p style = \" width: 150px\" >" + massage + "</p></html>");
        massage1.setFont(new Font("Tahoma",Font.PLAIN, 16));
        massage1.setBackground(new Color(90,194,202));
        massage1.setOpaque(true);
        massage1.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(massage1);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(date.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        
        new Server();

        try {
            ServerSocket serve = new ServerSocket(9999);
            while(true){
                Socket s = serve.accept();

                DataInputStream dataIn = new DataInputStream(s.getInputStream());
                dataout = new DataOutputStream(s.getOutputStream());

                while(true){
                    String msg = dataIn.readUTF();
                    JPanel panel2 = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel2, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}