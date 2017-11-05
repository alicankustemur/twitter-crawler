package com.colendi.TwitterCrawler.unit;

import com.colendi.TwitterCrawler.configuration.CrawlerConfiguration;
import com.colendi.TwitterCrawler.service.CrawlerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.conf.ConfigurationBuilder;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by alicankustemur on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerServiceTest {

    @Mock
    private CrawlerConfiguration crawlerConfiguration;

    @InjectMocks
    @Autowired
    private CrawlerService crawlerService;

    @Test
    public void givenCrawlerServiceWhenNewConfigurationBuilderThenCreateTwitterObject() {
        when(crawlerConfiguration.getConfigurationBuilder()).thenReturn(new ConfigurationBuilder());
        crawlerService.createTwitter();
    }

    @Test
    public void givenCrawlerServiceWhenGetUserAndPageNumberThenReturnTweetListByUserAndPageNumber() {
        when(crawlerConfiguration.getConfigurationBuilder()).thenReturn(new ConfigurationBuilder());

        Paging paging = new Paging(1, 100);
        try {
            crawlerService.createTwitter().getUserTimeline("", paging);
        } catch (Exception e) {
            assertTrue(e instanceof TwitterException);
        }

    }


}
