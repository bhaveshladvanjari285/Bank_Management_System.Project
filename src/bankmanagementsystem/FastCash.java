
package bankmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;


public class FastCash extends JFrame implements ActionListener  {
    
    JButton withdrawlrs100, withdrawlrs500, withdrawlrs2000, withdrawlrs5000 , withdrawlrs1000, withdrawlrs10000,exit;
    String pinnumber;
    
    FastCash (String pinnumber ) {
        this.pinnumber = pinnumber;
        setLayout(null);
        
        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);
        
        JLabel text = new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(210, 300, 700, 35);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD,16));
        image.add(text);
        
         withdrawlrs100= new JButton("RS 100");
        withdrawlrs100.setBounds(170, 415, 150, 30);
        withdrawlrs100.addActionListener(this);
        image.add(withdrawlrs100);
        
         withdrawlrs500 = new JButton("RS 500");
        withdrawlrs500.setBounds(355, 415, 150, 30);
        withdrawlrs500.addActionListener(this);
        image.add(withdrawlrs500);
        
        withdrawlrs1000 = new JButton("RS 1000");
        withdrawlrs1000.setBounds(170, 450, 150, 30);
        withdrawlrs1000.addActionListener(this);
        image.add(withdrawlrs100);
        
         withdrawlrs2000 = new JButton("RS 2000");
        withdrawlrs2000.setBounds(355, 450, 150, 30);
        withdrawlrs2000.addActionListener(this);
        image.add(withdrawlrs2000);
        
        withdrawlrs5000 = new JButton("RS 5000");
        withdrawlrs5000.setBounds(170, 485, 150, 30);
        withdrawlrs5000.addActionListener(this);
        image.add(withdrawlrs5000);
        
         withdrawlrs10000 = new JButton("RS 10000");
        withdrawlrs10000.setBounds(355, 485, 150, 30);
        withdrawlrs10000.addActionListener(this);
        image.add(withdrawlrs10000);
        
         exit = new JButton("BACK");
        exit.setBounds(355, 520, 150, 30);
        exit.addActionListener(this); 
        image.add(exit);
        
        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == exit){
         setVisible(false);
         new Transaction(pinnumber).setVisible(true);
        }else{
           String amount = ((JButton)ae.getSource()).getText().substring(3);
           Conn c = new Conn();
           try {
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()){
                     if(rs.getString("type").equals("Deposit")){
                         balance += Integer.parseInt(rs.getString("amount"));
                     }else{
                         balance -= Integer.parseInt(rs.getString("amount"));
                     }
                }
                if (ae.getSource() != exit && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null,"Insufficient Balance");
                    return;
                } 
                
                Date date = new Date();
                String query="insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawn', '"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "RS "+ amount + "Debited Sucessfully");
                
                setVisible(false);
                new Transaction(pinnumber).setVisible(true);
                } catch(Exception e){
                System.out.println(e);
                } 
         }
    }
    
    public static void main(String args[]){
        new FastCash("");
    
    }
}
