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

    public TweetServiceImpl(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @Override
    public List<Status> getAllTweetsByUser(String user) {
        int pageNumber = 1;
        List<Status> tweets = new ArrayList();
        while (true) {
            try {
                int size = tweets.size();
                List list = crawlerService.getTweetsByUserAndPageNumber(user,pageNumber++);
                tweets.addAll(list);
                if(crawlerService.isTweetSizeEqualUserAllTweetSize(tweets,size)){
                    break;
                }
            }
            catch(TwitterException e) {
                e.printStackTrace();
            }
        }

        return tweets;
    }

    @Override
    public Status getStatusById(Long id) {
        try {
            return crawlerService.createTwitter().showStatus(id);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
