package com.colendi.TwitterCrawler.service;

import com.colendi.TwitterCrawler.configuration.CrawlerConfiguration;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by alicankustemur on 30/10/2017.
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {

    private final CrawlerConfiguration crawlerConfiguration;
    private Twitter twitter;

    public CrawlerServiceImpl(CrawlerConfiguration crawlerConfiguration) {
        this.crawlerConfiguration = crawlerConfiguration;
    }

    @Override
    public Twitter createTwitter() {
        ConfigurationBuilder configurationBuilder = crawlerConfiguration.getConfigurationBuilder();
        if (twitter == null) {
            twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        }
        return twitter;
    }

    @Override
    public List<Status> getTweetsByUserAndPageNumber(String user, int pageNumber) throws TwitterException {
        Paging page = new Paging(pageNumber, 100);
        return createTwitter().getUserTimeline(user, page);
    }


}
