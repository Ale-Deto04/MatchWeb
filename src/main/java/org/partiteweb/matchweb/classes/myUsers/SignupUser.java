package org.partiteweb.matchweb.classes.myUsers;

import java.time.LocalDate;

public class SignupUser extends InfoUser {

    //Used for register a new user
    private String password;
    private String sport;
    private String team;

    public SignupUser(String name, String surname, String username, String role, LocalDate birthday, String email, String password, String sport, String team) {
        super(name, surname, username, role, birthday, email);
        this.password = password;
        this.sport = sport;
        this.team = team;
    }

    //Getters
    public String getPassword(){return password;}
    public String getSport() {return sport;}
    public String getTeam() {return team;}

    //Setters
    public void setPassword(String password) {this.password = password;}
    public void setSport(String sport) {this.sport = sport;}
    public void setTeam(String team) {this.team = team;}
}
