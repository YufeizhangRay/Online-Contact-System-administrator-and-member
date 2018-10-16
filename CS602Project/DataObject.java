package Yufei;
import java.io.Serializable;

class DataObject implements Serializable{

	protected String message;

	DataObject(){
		message = "";
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String inMessage){
		message = inMessage;
	}
}

class LogInDataObject extends DataObject{
	private String username;
	private String password;
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
}

class UpdatedDataObject extends DataObject{
        private String old;
        private String updated;
	private String field;
        public void setold(String old){
             this.old = old;
        }
        public String getold(){
             return old;
        }
        public void setupdated(String updated){
             this.updated = updated;
        }
        public String getupdated(){
             return updated;
        }
	public void setfield(String field){
		this.field = field;
	}
	public String getfield(){
		return field;
	}
}

class InsertedDataObject extends DataObject{
	private String name;
	private String phone;
	private String username;
	public void setname(String name){
		this.name = name;
	}
	public String getname(){
		return name;
	}
	public void setphone(String phone){
		this.phone = phone;
	}
	public String getphone(){
		return phone;
	}
	public void setusername(String username){
		this.username = username;
	}
	public String getusername(){
		return username;
	}
}


class DeletedDataObject extends DataObject{
	private String phone;
	private String username;
	public void setphone(String phone){
		this.phone = phone;
	}
	public String getphone(){
		return phone;
	}
	public void setusername(String username){
		this.username = username;
	}
	public String getusername(){
		return username;
	}
}


class SearchDataObject extends DataObject{
	private String username;
	public void setusername(String username){
		this.username = username;
	}
	public String getusername(){
		return username;
	}
}
