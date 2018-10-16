package Yufei;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client{
		private JFrame mainFrame;
		private JLabel headerLabel;
		private JLabel statusLabel;
		private JPanel controlPanel;
	static String username = null;
	static Scanner sc = null;
        static Socket socketToServer = null;
        static ObjectOutputStream myOutputStream =null;
        static ObjectInputStream myInputStream =null;
	static{
	try{
		sc = new Scanner(System.in);
		socketToServer = new Socket("127.0.0.1", 3000);
	        myOutputStream =new ObjectOutputStream(socketToServer.getOutputStream());
        	myInputStream =new ObjectInputStream(socketToServer.getInputStream());
	}
	catch(IOException e){System.out.println(e);}
	}
	public void login() throws Exception{
/*		mainFrame = new JFrame("CS602");
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(3,1));
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
			});
		headerLabel = new JLabel("",JLabel.CENTER);
		statusLabel = new JLabel("",JLabel.CENTER);
		statusLabel.setSize(350,100);
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		headerLabel.setText("Control in action: JTextField");
	        JLabel  namelabel= new JLabel("User ID: ", JLabel.RIGHT);
	        JLabel  passwordLabel = new JLabel("Password: ", JLabel.CENTER);
	        final JTextField userText = new JTextField(20);
	        final JPasswordField passwordText = new JPasswordField(20);
        	JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		String data = "Username " + userText.getText();
		data += ", Password: "+ new String(passwordText.getPassword());
		statusLabel.setText(data);
		}
		});
                controlPanel.add(namelabel);
	        controlPanel.add(userText);
                controlPanel.add(passwordLabel);
	        controlPanel.add(passwordText);
	        controlPanel.add(loginButton);
		mainFrame.setVisible(true);*/
		while(true){
			LogInDataObject myObject = new LogInDataObject();
			DataObject chen = new DataObject();
			System.out.println("login with 1");
			System.out.println("exit with 0");
			String a = sc.next();
			if(a.equals("1")){
			System.out.println("username");
			username = sc.next();
			myObject.setUsername(username);
			System.out.println("password");
			myObject.setPassword(sc.next());
			myOutputStream.writeObject(myObject);
			DataObject chen2 = new DataObject();
			chen2 = (DataObject)myInputStream.readObject();
			System.out.println("Messaged received : " + chen2.getMessage());
			if(chen2.getMessage().equals("success"))
			{
			break;
			}else{
				continue;
			}
			}else if(a.equals("0")){
			                      
		 socketToServer.close();
                           System.exit(0);
		}else{  
                 System.out.println("Type wrong !!");
		}
		}
	}
	public void update(){
		try{	
		DataObject chen = new DataObject();
		UpdatedDataObject upObject = new UpdatedDataObject();
		chen.setMessage("2");
		myOutputStream.writeObject(chen);
		boolean flag = true;
		while(flag){
			System.out.println("change 1. username, change 2. phone number");
			String cas = sc.next();
			switch(cas){
			         case "1": upObject.setfield("name");
				 System.out.println("name");
				 upObject.setold(sc.next());
				 System.out.println("new name");
				 upObject.setupdated(sc.next());
				 System.out.println(upObject.getold()+upObject.getupdated());
				 myOutputStream.writeObject(upObject);
				 flag = false;
				 break;
			         case "2": upObject.setfield("phone number");
				 System.out.println("phone-number");
				 upObject.setold(sc.next());
				 System.out.println("new phone number");
				 upObject.setupdated(sc.next());
				 myOutputStream.writeObject(upObject);
				 flag = false;
				 break;
			}
			DataObject chen1 = new DataObject();
			chen1 = (DataObject)myInputStream.readObject();
			System.out.println("Messaged received : " + chen1.getMessage());
		}
		}
		catch(Exception e){
                       System.out.println(e);
		}
	}
	public void insert(){
		try{
			DataObject chen = new DataObject();
			InsertedDataObject inObject = new InsertedDataObject();
			chen.setMessage("3");
			myOutputStream.writeObject(chen);
			System.out.println("1. enter the name, 2. enter phone-number");
			inObject.setname(sc.next());
			inObject.setphone(sc.next());
			inObject.setusername(username);
			myOutputStream.writeObject(inObject);
			DataObject chen1 = new DataObject();
			chen1 = (DataObject)myInputStream.readObject();
			System.out.println("Messaged received : " + chen1.getMessage());
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void delete(){
		try{
			DataObject chen = new DataObject();
			DeletedDataObject deObject = new DeletedDataObject();
			chen.setMessage("4");
			myOutputStream.writeObject(chen);
			System.out.println("1. enter phone-number");
			deObject.setphone(sc.next());
			deObject.setusername(username);
			myOutputStream.writeObject(deObject);
			DataObject chen1 = new DataObject();
			chen1 = (DataObject)myInputStream.readObject();
			System.out.println("Messaged received : " + chen1.getMessage());
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public void search(){
		try{
			DataObject chen = new DataObject();
			SearchDataObject seObject = new SearchDataObject();
			chen.setMessage("5");
			myOutputStream.writeObject(chen);
			seObject.setusername(username);
			myOutputStream.writeObject(seObject);
			DataObject chen1 = new DataObject();
			chen1 = (DataObject)myInputStream.readObject();
			System.out.println("Messaged received : \n" + chen1.getMessage());
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void fuc() throws Exception{
		while(true){
			System.out.println("1. logout, 2. update, 3. insert, 4. delete, 5. search, 0. exit");
			String cas = sc.next();
			DataObject chen = new DataObject();
			switch(cas){
				case "1":System.out.println("Are you sure logging out??");
				System.out.println("Yes/No");
				String q = sc.next();
				if(q.equals("Yes")){
				chen.setMessage("1");
				myOutputStream.writeObject(chen);
				System.out.println(chen.getMessage());
				login();
				}
				else if(q.equals("No")){
					continue;
				}else{
					System.out.println("type wrong, please type again");
					continue;
				}
				break;
				case "2": update();break;
				case "3": insert();break;
				case "4": delete();break;
				case "5": search();break;
				case "0": System.out.println("Plz come back again~~");System.exit(0);
				default: System.out.println("type wrong");
			}
		}
	}
	public static void main(String[] args){
	try{
		Client a = new Client();
		a.login();
		a.fuc();
	}catch(IOException e){
		System.out.println(e);
	}
	catch(Exception e){
		System.out.println(e);
	}
	}
}
