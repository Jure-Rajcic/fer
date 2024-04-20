package hr.fer.oprpp2.p08.model;

public class PollOptions {


  
    private long id;
    private String optionTitle;
    private String optionLink;
    private long pollID;
    private long votesCount;

    public PollOptions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String title) {
        this.optionTitle = title;
    }

    public String getOptionLink() {
        return optionLink;
    }

    public void setOptionLink(String link) {
        this.optionLink = link;
    }


    public long getPollID() {
        return pollID;
    }

    public void setPollID(long pollID) {
        this.pollID = pollID;
    }


    public long getVotesCount() {
        return votesCount;
    }


    public void setVotesCount(long votesCount) {
        this.votesCount = votesCount;
    }

    
}
