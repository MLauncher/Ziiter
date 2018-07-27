package serverfiles;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import HibernateServerFiles2.User;

@Component
@Transactional
@Scope(value = "session")
public class Page_Changer {

	
	@Autowired	
	UseDAO use;
	
	@Autowired
	UseBean cur_user;
	String name;
	String user;
	boolean logged;
	String visib_but = "visible";
	String unf_but = "none";
	List<User> current_followers;
	@PostConstruct
	public void init()
	{
		
		use.setName("");
		user= cur_user.getUsername();
		logged = use.isLogin();
		
	}
	

	
	public String getVisib_but() {
		return visib_but;
	}

	public void setVisib_but(String visib_but) {
		this.visib_but = visib_but;
	}


	public String getUnf_but() {
		return unf_but;
	}



	public void setUnf_but(String unf_but) {
		this.unf_but = unf_but;
	}



	public String goToUserPage(String username) {

		System.out.println(username + " The username that was sent goToUserPage");
		use.setName(username);
		user = cur_user.getUsername();
		System.out.println(user + " the current user on page turn");
		if (use.getFollowers(user).isEmpty()) {
			
			System.out.println("You are not following this guy!");
			return "UserPage";
		} else {
			current_followers = use.getFollowers(user);
			for (User follower : current_followers) {
				System.out.println(follower.getUsername()
						+ "the name of the follower");
				System.out.println(username
						+ "the username sent to page changer");
				if (follower.getUsername().equals(username)) {
					System.out.println("You are current following this guy!");
					visib_but = "none";
					unf_but = "visible";
				}
			}
			return "UserPage";
		}

	}
	
	public String goToYourPage(String username,String password)
	{
		
		String goat = use.log_in(username, password);
		if(goat == "Success")
			return "Success";
		else
			return "Failure";

	}
	public String goToHomePage()
	{
		logged = use.isLogin();
		if(logged)
		{
			System.out.println("is logged in? " + logged);
			
		
			return "Success.xhtml";
		}
		else
		{
			System.out.println("is logged in? " + logged);
			return "Home_Page.xhtml";
		}
	}
}
