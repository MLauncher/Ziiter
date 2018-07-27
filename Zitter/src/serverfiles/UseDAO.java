package serverfiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import HibernateServerFiles2.Message;
import HibernateServerFiles2.Relationship;
import HibernateServerFiles2.Replies;
import HibernateServerFiles2.User;

@Component
@Transactional
@Scope(value = "session")

public class UseDAO {
	
	@Autowired
	UseBean myuser;
	
	@Autowired
	SessionFactory sessionfactory;
	
//	@Autowired
//	Page_Changer page;
	
	List<Message> messages;
	List<User> followers;
	String phrase = "";
	
	String name = "";
	String reply = "";
	
	String gotmess;
	String successuser = "";
	String hidden_text = "text";
	String hidden_text2 = "hidden";
	String button_display = "visible";
	String username_display = "Username";
	String password_display ="Password";
	
	boolean login = false; 
	
	
	


	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public UseBean getMyuser() {
		return myuser;
	}

	public void setMyuser(UseBean myuser) {
		this.myuser = myuser;
	}

	public String getGotmess() {
		return gotmess;
	}


	public void setGotmess(String gotmess) {
		this.gotmess = gotmess;
	}

	
	
	public String getHidden_text() {
		return hidden_text;
	}


	public void setHidden_text(String hidden_text) {
		this.hidden_text = hidden_text;
	}

	public void makeHidden()
	{
		hidden_text = "hidden";
	}
	
	public void makeText()
	{
		hidden_text = "text";
	}
	
	public String getHidden_text2() {
		return hidden_text2;
	}


	public void setHidden_text2(String hidden_text2) {
		this.hidden_text2 = hidden_text2;
	}
	
	public void makeHidden2()
	{
		hidden_text2 = "hidden";
	}
	
	public void  makeText2()
	{
		hidden_text2 = "text";
	}


	public String getButton_display() {
		return button_display;
	}


	public void setButton_display(String button_display) {
		this.button_display = button_display;
	}
	
	public void makeNone()
	{
		button_display = "none";
	}
	
	public void makeVisible()
	{
		button_display = "visible";
	}

	public String getUsername_display() {
		return username_display;
	}


	public void setUsername_display(String username_display) {
		this.username_display = username_display;
	}


	public String getPassword_display() {
		return password_display;
	}


	public void setPassword_display(String password_display) {
		this.password_display = password_display;
	}


	public String getName() {
		return name;
	}


	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getSuccessuser() {
		return successuser;
	}


	public void setSuccessuser(String successuser) {
		this.successuser = successuser;
	}


	

	public void setLog_in(boolean log_in) {
		this.login = log_in;
	}

	
	
	public String getPhrase() {
		return phrase;
	}


	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}


	
	
	public Session getCurrentSession()
	{
		return sessionfactory.getCurrentSession();
	}
	
	
	public String log_in(String user, String pass)
	{
		System.out.println(user + " The username sent to the bean");
		Query query = getCurrentSession().createQuery("from User u where u.username = :name ");
		query.setString("name",user );
		
		if(query.uniqueResult() == null)
		{
			phrase = "User and password combination does not exist";
			return phrase;
		}
		else if(query.uniqueResult() != null) 
		{
			User l_User = (User)query.uniqueResult();
			
			if (l_User.getPassword().equals(pass))
				{
					//phrase = "You have successfully logged in"; 
					login = true;
					phrase = "Success";
					makeHidden();
					makeText2();
					makeNone();
					username_display = "";
					password_display = "";
					successuser = user;
					return "Success";
				}
			else
				phrase = "Your password is wrong " + myuser.username; 
				return phrase; 
		}
		
		return phrase;
		
		
	}	
  public List<Message> printAllTweets()
  {
	  
	  Query query = getCurrentSession().createQuery("from Message order by id desc");
	  Hibernate.initialize(query);
	  Hibernate.initialize(messages);
	  messages = query.list();
	  
	  for(Message m: messages)
	  {
		  Hibernate.initialize(m.getUser().getUsername());
		  Hibernate.initialize(m.getMessage());
	  }
	  
	  //System.out.println(messages.get(0).getMessage());
	  return messages;
	 
  }
	
 public List<Message> printTweets(String name)
 {
	 System.out.println(name + " is sent");
	 Query query = getCurrentSession().createQuery("from User u where u.username = :name ");
	 query.setString("name", name);
	 
	 User user = (User)query.uniqueResult();
	 List<Message> user_message = new ArrayList<Message>();
	 
	 if(user.getMessages().isEmpty())
	 {
		 Message nullmess = new Message();
		 nullmess.setId(0);
		 nullmess.setMessage("No messages");
		 nullmess.setUser(user);
		 
		 user_message.add(nullmess);
		 return user_message;
	 }
	 user_message.addAll(user.getMessages());
	 return user_message;
 }
 
 public void follow(String vname)
 {
	 System.out.println(vname + " The name sent;");
	 Query query = getCurrentSession().createQuery("from User u where u.username = :name ");
	 query.setString("name", vname);
	 
	 User person_being_followed = (User) query.uniqueResult();
	 
	 Query query2 = getCurrentSession().createQuery("from User u where u.username = :name ");
	 query2.setString("name", this.name);
	 
	User follower = (User)query2.uniqueResult();
	Relationship rea = new Relationship(person_being_followed, follower);
	getCurrentSession().save(rea);
	System.out.println("Check the DataBase"); 
	 
 }
 
 public String addMess(String name,String phrase)
 {
	 System.out.println("My phrase is " + phrase);
	 Query query = getCurrentSession().createQuery("from User u where u.username = :name ");
	 query.setString("name", name);
	 
	 User user = (User)query.uniqueResult();
	 Message message = new Message(user, phrase);
	 
	 getCurrentSession().save(message);
	 return "Completed";
 }
 
 public List<User> getFollowers(String name)
 {
	 Query query = getCurrentSession().createQuery("from User u where u.username = :name ");
	 query.setString("name", name);
	 
	 System.out.println("The name given " + name);
	 User followers = (User)query.uniqueResult();
	 Hibernate.initialize(followers);
	 
	 List<Relationship> myrea = new ArrayList<Relationship>();
	 
//	 if (followers.getRelationshipsForFollower().isEmpty())
//	 {
//		 System.out.println("The list is empty!");
//		 List<User> emptylist = new ArrayList<User>();
//		 
//		 return emptylist;
//	 }
	 myrea.addAll(followers.getRelationshipsForFollower());
	 
	 List<User> myfoll = new ArrayList<User>();
	 for(Relationship r: myrea)
	 {
		 
		 User u = r.getUserByFollowee();
		 
		 Hibernate.initialize(u);
		 myfoll.add(u);
	 }
	 
	 return myfoll;
 }
 
	public String addAccount(String user, String pass) {
		Query query = getCurrentSession().createQuery(
				"from User where username = :name");
		query.setString("name", user);

		if (query.uniqueResult() == null) {
			User new_user = new User(user, pass);
			getCurrentSession().save(new_user);
			login = true;
			phrase = "Success";
			makeHidden();
			makeText2();
			makeNone();
			username_display = "";
			password_display = "";
			successuser = user;
			return "Success";

		}

		else {
			phrase = "Username already exists";
			return phrase;
		}

	}
 
 public List<Replies> getReplies(Message parent_mess)
 {
	 Query query = getCurrentSession().createQuery("from Message where message = :name");
	 query.setString("name",parent_mess.getMessage());
	 Message m = (Message)query.uniqueResult();
	 
	 
	 List<Replies> replies = new ArrayList<Replies>();
	 replies.addAll(m.getUser().getReplieses());
	 
	 for(Replies r: replies)
	 {
		 System.out.println(r.getReply());
		 System.out.println(r.getMessage().getUser().getUsername());
	 }
	 
	 return replies;
 }
 
 
 
 public void addReply(String user)
 {
	 Query query = getCurrentSession().createQuery("from User where username = :name ");
	 query.setString("name", user);
	 
	 User current_user = (User)query.uniqueResult();//Brian
	 
	 Message ms = new Message(current_user,this.reply);
	 
	 Query query2 = getCurrentSession().createQuery("from User where username = :name");
	 query2.setString("name", name);//Law
	 
	 User user_being_replied_to = (User)query2.uniqueResult();
	 Replies reply = new Replies(ms,user_being_replied_to,ms.getMessage());
	 
	 
	 getCurrentSession().save(ms);
	 
	 getCurrentSession().save(reply);
	 this.reply = "";
 }
 
 public String getImage() throws IOException
 {
	
	BufferedImage image = ImageIO.read(new File("C:/Users/SonicZero46/Pictures/OH_Tulip02.jpg"));
	
	
//	int type = image.getType();
//	
//	BufferedImage resizedimg = resizeImage(image, type);
	ImageIO.write(image, "jpg", new File("C:\\Users\\SonicZero46\\Documents\\Ftdworkspace\\Zitter\\WebContent\\Pictures\\OH_Tulip02.jpg"));
	return  "C:\\Users\\SonicZero46\\Documents\\Ftdworkspace\\Zitter\\WebContent\\Pictures\\OH_Tulip02.jpg";
 }
 //This is to be used for when ever I have to resize an image in java
// private static BufferedImage resizeImage(BufferedImage originalImage, int type){
//		BufferedImage resizedImage = new BufferedImage(50, 50, type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, 50, 50, null);
//		g.dispose();
//	 
//		return resizedImage;
//	    }

}
