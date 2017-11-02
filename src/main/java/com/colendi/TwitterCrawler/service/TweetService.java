package com.colendi.TwitterCrawler.service;

import twitter4j.Status;

import java.util.List;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface TweetService {
    public List<Status> getAllTweetsByUser(String user);
}
