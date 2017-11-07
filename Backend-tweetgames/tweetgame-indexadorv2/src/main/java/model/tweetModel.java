package model;

public class tweetModel {

    private String docID;
    private String tweetID;
    private String userId;
    private String userName;
    private String text;

    public tweetModel(){};

    public tweetModel(String docID, String tweetID, String userId, String userName, String text) {
        this.docID = docID;
        this.tweetID = tweetID;
        this.userId = userId;
        this.userName = userName;
        this.text = text;
    }

    public String getDocID() {
        return docID;
    }
    public void setDocID(String docID) {
        this.docID = docID;
    }
    public String getTweetID() {
        return tweetID;
    }
    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
