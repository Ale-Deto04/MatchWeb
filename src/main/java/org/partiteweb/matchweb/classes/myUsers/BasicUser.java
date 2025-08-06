package org.partiteweb.matchweb.classes.myUsers;

public abstract class BasicUser implements Comparable<BasicUser> {
    private String name;
    private String surname;
    private String username;
    private String role;

    public BasicUser(String name, String surname, String username, String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
    }

    //Getters
    public String getName() {return name;}
    public String getSurname() {return surname;}
    public String getUsername() {return username;}
    public String getRole() {return role;}

    //Setters
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setUsername(String username) {this.username = username;}

    public boolean hasEqualUsername(String username) {
        return this.username.equals(username);
    }

    @Override
    public int compareTo(BasicUser o) {
        return this.surname.compareTo(o.getSurname());
    }
}
