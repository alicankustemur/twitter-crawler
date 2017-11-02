package com.colendi.TwitterCrawler.service;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface CrawlerService {
    public Twitter createTwitter();
    public List<Status> getTweetsByUserAndPageNumber(String user,int pageNumber) throws TwitterException;
}
