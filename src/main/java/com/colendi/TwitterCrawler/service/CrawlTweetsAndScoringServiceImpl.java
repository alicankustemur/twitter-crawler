package com.colendi.TwitterCrawler.service;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by alicankustemur on 06/11/2017.
 */
@Service
public class CrawlTweetsAndScoringServiceImpl implements CrawlTweetsAndScoringService {

    private final TweetService tweetService;
    private final FriendShipCrudService friendShipCrudService;
    private final FriendShipScoringService friendShipScoringService;

    public CrawlTweetsAndScoringServiceImpl(TweetService tweetService, FriendShipCrudService friendShipCrudService, FriendShipScoringService friendShipScoringService) {
        this.tweetService = tweetService;
        this.friendShipCrudService = friendShipCrudService;
        this.friendShipScoringService = friendShipScoringService;
    }


    @Override
    public Map getScoringAllCrawlTweetsByUser(String user) {
        List<Status> statuses = tweetService.getAllTweetsByUser(user);
        friendShipCrudService.removeAll();
        for (Status status : statuses) {
            UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
            friendShipCrudService.saveFriendShipList(status,userMentionEntities);
        }

        return friendShipScoringService.calculateAndGetAllScores();
    }
}
