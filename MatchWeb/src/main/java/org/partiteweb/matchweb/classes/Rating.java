package org.partiteweb.matchweb.classes;

import java.time.LocalDate;

public class Rating {
    private int rate;
    private String author;
    private String date;
    private String text;

    public Rating(int rate, String author, String date, String text) {
        this.rate = rate;
        this.author = author;
        this.date = date;
        this.text = text;
    }

    //Setters
    public void setRate(int rate) {this.rate = rate;}
    public void setAuthor(String author) {this.author = author;}
    public void setDate(String date) {this.date = date;}
    public void setText(String text) {this.text = text;}

    //Getters
    public int getRate() {return rate;}
    public String getAuthor() {return author;}
    public String getDate() {return date;}
    public String getText() {return text;}
}
