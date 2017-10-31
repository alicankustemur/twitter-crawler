package com.colendi.TwitterCrawler;

import com.colendi.TwitterCrawler.service.FriendShipCrudService;
import com.colendi.TwitterCrawler.service.TweetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.*;

import java.util.List;

/**
 * Created by alicankustemur on 29/10/2017.
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final TweetService tweetService;
    private final FriendShipCrudService friendShipCrudService;

    public CrawlerController(TweetService tweetService, FriendShipCrudService friendShipCrudService) {
        this.tweetService = tweetService;
        this.friendShipCrudService = friendShipCrudService;
    }

    @GetMapping("{user}")
    public void saveUserAndMentionUserAndTweetIdByUser(@PathVariable String user) {
        List<Status> statuses = tweetService.getAllTweetsByUser(user);
        friendShipCrudService.removeAll();
        for (Status status : statuses) {
            UserMentionEntity[] userMentionEntities = status.getUserMentionEntities();
            friendShipCrudService.saveFriendShip(status,userMentionEntities);
        }

    }


    /*
    @GetMapping("/status/{id}")
    public Status getTweetById(@PathVariable Long id){
        FriendShip friendShip = friendShipRepository.findByTweetId(id);
        return tweetService.getStatusById(friendShip.getTweetId());
    }
    */


}
