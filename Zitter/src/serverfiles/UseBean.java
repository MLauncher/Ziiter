package serverfiles;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UseBean {
	String username;
	String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void printSomething()
	{
		System.out.println("Print something " + username);
	}
	public String helloName()
	{
		printSomething();
		 if("".equals(username) || username ==null){
				return "a";
			   }else{
				return "Welcome " + username;
			   }
		
	}

}
