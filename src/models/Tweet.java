package models;

public class Tweet {
	protected String user;
	protected String text;
	protected Polarite polarite;
	
	public Tweet(){
		this.polarite = Polarite.UNDEFINED ;
	}
	
	public Tweet(String text){
		this();
		setText(text);
	}
	
	public Tweet(String text, int polarite){
		setText(text);
		setPolarite(polarite);
	}
	
	public void setText(String text){
		this.text = text ;
	}
	
	public void setUser(String user){
		this.user = user ;
	}
	
	public void setPolarite(int polarite){
		switch(polarite){
			case 0:
				this.polarite = Polarite.NEGATIF;
				break;
			case 2:
				this.polarite = Polarite.NEUTRE;
				break;
			case 4:
				this.polarite = Polarite.POSITIF;
				break;
		}
	}
	
	public void setPolarite(Polarite pol){
		this.polarite = pol ;
	}
	
	public String getText(){
		return text;
	}

	public String getUser() {
		return user; 
	}
	
}
