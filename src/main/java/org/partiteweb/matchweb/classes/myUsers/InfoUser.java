package org.partiteweb.matchweb.classes.myUsers;

import java.time.LocalDate;

public class InfoUser extends BasicUser {

   //Used for retrieving data
    private LocalDate birthday;
    private String email;

    public InfoUser(String name, String surname, String username, String role, LocalDate birthday, String email) {
        super(name, surname, username, role);
        this.birthday = birthday;
        this.email = email;
    }

    //Getters
    public LocalDate getBirthday() {return birthday;}
    public String getEmail(){return email;}

    //Setters
    public void setBirthday(LocalDate birthday) {this.birthday = birthday;}
    public void setEmail(String email) {this.email = email;}
}
