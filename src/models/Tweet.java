package models;

public class Tweet {
	protected String user;
	protected String text;
	protected Polarite polarite;
	
	public Tweet(){
		this.polarite = Polarite.UNDEFINED ;
	}
	
	public void setText(String text){
		this.text = text ;
	}
	public void setUser(String user){
		this.user = user ;
	}
	public void setPolarite(Polarite pol){
		this.polarite = pol ;
	}
}
