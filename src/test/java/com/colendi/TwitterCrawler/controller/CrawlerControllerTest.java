package com.colendi.TwitterCrawler.controller;

import com.colendi.TwitterCrawler.CrawlerController;
import com.colendi.TwitterCrawler.service.FriendShipCrudService;
import com.colendi.TwitterCrawler.service.FriendShipScoringService;
import com.colendi.TwitterCrawler.service.TweetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alicankustemur on 05/11/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CrawlerController.class)
public class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetService tweetService;

    @MockBean
    private FriendShipCrudService friendShipCrudService;

    @MockBean
    FriendShipScoringService friendShipScoringService;

    @Test
    public void givenCrawlerControllerWhenTwitterUsernameThenScoringAccountsFriends() throws Exception {
        String url = "http://localhost:8080/crawler/cmylmz";
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CrawlerController.class))
                .andExpect(handler().methodName("saveFriendShipAndGetAllFriendShipScore"));;

    }


}
