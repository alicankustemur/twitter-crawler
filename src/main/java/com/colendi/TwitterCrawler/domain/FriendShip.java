package com.colendi.TwitterCrawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "friendship")
public class FriendShip {

    @Id
    private String id;
    private String username;
    private Long tweetId;
    private String mentionFriend;

    public FriendShip() {
    }

    public FriendShip(String id, String username, Long tweetId, String mentionFriend) {
        this.id = id;
        this.username = username;
        this.tweetId = tweetId;
        this.mentionFriend = mentionFriend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public String getMentionFriend() {
        return mentionFriend;
    }

    public void setMentionFriend(String mentionFriend) {
        this.mentionFriend = mentionFriend;
    }
}
