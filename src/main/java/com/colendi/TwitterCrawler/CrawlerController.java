package com.colendi.TwitterCrawler;

import com.colendi.TwitterCrawler.service.FriendShipCrudService;
import com.colendi.TwitterCrawler.service.FriendShipScoringService;
import com.colendi.TwitterCrawler.service.TweetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;

import java.util.List;
import java.util.Map;

/**
 * Created by alicankustemur on 29/10/2017.
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final TweetService tweetService;
    private final FriendShipCrudService friendShipCrudService;
    private final FriendShipScoringService friendShipScoringService;

    public CrawlerController(TweetService tweetService, FriendShipCrudService friendShipCrudService, FriendShipScoringService friendShipScoringService) {
        this.tweetService = tweetService;
        this.friendShipCrudService = friendShipCrudService;
        this.friendShipScoringService = friendShipScoringService;
    }

    @GetMapping("{user}")
    public Map saveFriendShipAndGetAllFriendShipScore(@PathVariable String user) {
        List<Status> statuses = tweetService.getAllTweetsByUser(user);
        friendShipCrudService.removeAll();
        for (Status status : statuses) {
            UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
            friendShipCrudService.saveFriendShip(status,userMentionEntities);
        }

        return friendShipScoringService.calculateAndGetAllScores();
    }

}
