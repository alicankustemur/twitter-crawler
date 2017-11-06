package com.colendi.TwitterCrawler.controller;

import com.colendi.TwitterCrawler.service.CrawlTweetsAndScoringService;
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

    private final CrawlTweetsAndScoringService crawlTweetsAndScoringService;

    public CrawlerController(CrawlTweetsAndScoringService crawlTweetsAndScoringService) {
        this.crawlTweetsAndScoringService = crawlTweetsAndScoringService;
    }

    @GetMapping("{user}")
    public Map getTwitterCrawler(@PathVariable String user) {
       return crawlTweetsAndScoringService.getScoringAllCrawlTweetsByUser(user);
    }

}
