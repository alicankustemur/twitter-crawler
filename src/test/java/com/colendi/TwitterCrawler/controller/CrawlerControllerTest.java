package com.colendi.TwitterCrawler.controller;

import com.colendi.TwitterCrawler.service.CrawlTweetsAndScoringService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    CrawlTweetsAndScoringService crawlTweetsAndScoringService;

    @Test
    public void givenCrawlerControllerWhenTwitterUsernameThenScoringAccountsFriends() throws Exception {
        String url = "http://localhost:8080/crawler/cmylmz";
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(CrawlerController.class))
                .andExpect(handler().methodName("getTwitterCrawler"));;

    }


}
