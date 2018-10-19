package com.example.demo;

import javax.persistence.*;

/**
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 */
@Entity //(name = "greetings")
@Table(name = "greetings")
//@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Greeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String content;

    public Greeting() {
        this.id = id;
        this.content = content;
    }

    public Greeting(String content) {
        this.content = content;
    }

    public Greeting(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
