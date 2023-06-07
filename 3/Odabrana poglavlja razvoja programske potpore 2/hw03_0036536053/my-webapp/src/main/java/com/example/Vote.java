package com.example;

public class Vote {

    public static final String SEPARATOR = ",";

    private String id;
    private String name;
    private String link;
    private int votes;

    public Vote(String line) {
        String[] parts = line.split(SEPARATOR);
        this.id = parts[0];
        this.name = parts[1];
        this.link = parts[2];
        this.votes = 0;
    }
    
    public Vote(String id, String name, String link, int votes) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
    
}
