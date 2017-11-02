package com.colendi.TwitterCrawler.service;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alicankustemur on 30/10/2017.
 */
@Service
public class TweetServiceImpl implements TweetService {

    private final CrawlerService crawlerService;
    private List<Status> tweets;

    public TweetServiceImpl(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @Override
    public List<Status> getAllTweetsByUser(String user) {
        int pageNumber = 1;
        tweets = new ArrayList();
        while (true) {
            int tweetSize = tweets.size();
            addTweetsByUserAndPageNumberToList(user,pageNumber++);
            if (this.isTweetSizeEqualUserAllTweetSize(tweets, tweetSize)) {
                break;
            }
        }

        return tweets;
    }

    public void addTweetsByUserAndPageNumberToList(String user, Integer pageNumber) {
        try {
            List list = crawlerService.getTweetsByUserAndPageNumber(user, pageNumber);
            tweets.addAll(list);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    public boolean isTweetSizeEqualUserAllTweetSize(List<Status> list, int tweetSize) {
        return list.size() == tweetSize ? true : false;
    }

}
