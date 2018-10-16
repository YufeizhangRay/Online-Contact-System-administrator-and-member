package Yufei;
import java.io.*;
import java.net.*;
import java.sql.*;


public class ThreadedDataObjectServer
{  public static void main(String[] args ) 
   {  
	System.out.println("Welcome to use Yufei's server");	
      try 
      {  ServerSocket s = new ServerSocket(3000);
         
         for (;;)
         {  Socket incoming = s.accept( );
            new ThreadedDataObjectHandler(incoming).start();
             
	   	 }   
      }
      catch (Exception e) 
      {  System.out.println(e);
      } 
   } 
}

class ThreadedDataObjectHandler extends Thread
{  
	public ThreadedDataObjectHandler(Socket i) 
   { 
   		incoming = i;
   }
   public void run(){ 
   try{		
		Connection con = DBUtil.getConnection();
		DatabaseMetaData dm = con.getMetaData();
		dm.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
		dm.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
		
		login(in,stmt,out);
		while(true){
			numObject = (DataObject)in.readObject();
			String cas = numObject.getMessage();
                        switch(cas){
                        case "1": login(in,stmt,out);break;
                        case "2": update(in,stmt,out);break;
			case "3": insert(in,stmt,out);break;
			case "4": delete(in,stmt,out);break;
			case "5": search(in,stmt,out);break;
		}
		}

		}
		catch(Exception e){
			System.out.println(e);
		}
   }
   public void search(ObjectInputStream in, Statement stmt, ObjectOutputStream out)throws Exception{
	  	SearchDataObject fei = new SearchDataObject();
                fei = (SearchDataObject)in.readObject();
                String user = fei.getusername();
	  	DataObject DO = new DataObject();
		StringBuffer sb = new StringBuffer();
	  	if(auth.equals("1")){
			ResultSet sql = stmt.executeQuery("select * from `contact`");	
			while(sql.next()){
				sb.append(sql.getString("name")+" "+sql.getString("phone number")+" "+sql.getString("username")+"\n");
			}
			String ufei = sb.toString();
			DO.setMessage(ufei);
			out.writeObject(DO);
		}
		else{
			ResultSet sql = stmt.executeQuery("select * from `contact` where `username` = '"+user+"'" );
			while(sql.next()){
				sb.append(sql.getString("name")+" "+sql.getString("phone number")+" "+sql.getString("username")+"\n");
			}
			String ufei = sb.toString();                      
			DO.setMessage(ufei);      
			out.writeObject(DO);
		}
}
public void login (ObjectInputStream in, Statement stmt, ObjectOutputStream out) throws Exception{
		ResultSet rset = stmt.executeQuery("select * from login");
		boolean flag =  true;
		while(flag){
		DataObject bo = new DataObject();
		LogInDataObject myObject = new LogInDataObject();
                myObject = (LogInDataObject)in.readObject();
		String user = myObject.getUsername();
		String pass = myObject.getPassword();
		rset.beforeFirst();
		while(rset.next()){
				if(rset.getString("username").equals(user)&&rset.getString("password").equals(pass)){
					bo.setMessage("success");
					auth = rset.getString("authorization");
					out.writeObject(bo);
					flag = false;
					break;
				}else{
				 if(rset.next()!=false){
                                        rset.previous();
                                        continue;												                   }
				bo.setMessage("Username/Password wrong");
				out.writeObject(bo);
				}
			}
		}
   }
   public void update(ObjectInputStream in, Statement stmt, ObjectOutputStream out)throws Exception{
	   UpdatedDataObject fei = new UpdatedDataObject();
	   DataObject bo = new DataObject();
	   fei = (UpdatedDataObject)in.readObject();
	   String a = fei.getold();
	   String b = fei.getupdated();
	   String c = fei.getfield();
	   stmt.executeUpdate("UPDATE `contact` SET `"+c+"`='"+b+"' WHERE `"+c+"` = '"+a+"'");
	   bo.setMessage("Update success");
	   out.writeObject(bo);
   }
   public void insert(ObjectInputStream in, Statement stmt, ObjectOutputStream out)throws Exception{
	  	InsertedDataObject fei = new InsertedDataObject();
		DataObject bo = new DataObject();
                fei = (InsertedDataObject)in.readObject();
                String a = fei.getname();
		String b = fei.getphone();
		String c = fei.getusername();
		stmt.executeUpdate("INSERT INTO `contact`(`name`, `phone number`, `username`) VALUES ('"+a+"','"+b+"','"+c+"')");
		bo.setMessage("Insert success");
		out.writeObject(bo);
}

   public void delete(ObjectInputStream in, Statement stmt, ObjectOutputStream out)throws Exception{
	  	DeletedDataObject fei = new DeletedDataObject();
		DataObject bo = new DataObject();
                fei = (DeletedDataObject)in.readObject();
		String b = fei.getphone();
		String c = fei.getusername();
		stmt.executeUpdate("DELETE FROM `contact` WHERE `phone number`='"+b+"'");
		bo.setMessage("Delete success");
		out.writeObject(bo);
}
   private Socket incoming;
   DataObject numObject = null;
   static String auth = null;
}
