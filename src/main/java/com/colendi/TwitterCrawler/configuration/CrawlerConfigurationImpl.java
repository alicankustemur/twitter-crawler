package com.colendi.TwitterCrawler.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by alicankustemur on 29/10/2017.
 */
@Configuration
public class CrawlerConfigurationImpl implements CrawlerConfiguration {

    private final Environment environment;

    public CrawlerConfigurationImpl(Environment environment) {
        this.environment = environment;
    }

    public ConfigurationBuilder getConfigurationBuilder() {
        return new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(environment.getProperty("crawler.consumerKey"))
                .setOAuthConsumerSecret(environment.getProperty("crawler.consumerSecret"))
                .setOAuthAccessToken(environment.getProperty("crawler.accessToken"))
                .setOAuthAccessTokenSecret(environment.getProperty("crawler.accessTokenSecret"));
    }

}
