package com.colendi.TwitterCrawler.unit;

import com.colendi.TwitterCrawler.repository.FriendShipRepository;
import com.colendi.TwitterCrawler.service.FriendShipService;
import com.colendi.TwitterCrawler.service.FriendShipServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserMentionEntity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alicankustemur on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class FriendShipServiceTest {

    @Mock
    private FriendShipRepository friendShipRepository;

    @Mock
    private UserMentionEntity userMentionEntity;

    @Mock
    private Status status;

    @Mock
    private User user;

    @InjectMocks
    private FriendShipServiceImpl friendShipService;

    @Test
    public void givenFriendShipServiceWhenGetTweetAndMentionUsersThenSaveThisFriendShip(){

        when(userMentionEntity.getScreenName()).thenReturn("veli");
        when(status.getId()).thenReturn(1234L);
        when(status.getUser()).thenReturn(user);
        when(status.getUser().getScreenName()).thenReturn("alican");

        UserMentionEntity[] userMentionEntities = {userMentionEntity};

        friendShipService.saveUserAndMentionUserAndTweetId(status, userMentionEntities);
    }

    @Test
    public void givenFriendShipServiceWhenGetTweetAndMentionUsersThenTrue(){
        when(status.getRetweetedStatus()).thenReturn(null);
        UserMentionEntity[] userMentionEntities = {userMentionEntity};
        assertTrue(friendShipService.isNoRetweetAndHasMentionFriend(status,userMentionEntities));
    }

    @Test
    public void givenFriendShipServiceWhenGetTweetAndMentionUsersThenFalse(){
        when(status.getRetweetedStatus()).thenReturn(null);
        UserMentionEntity[] userMentionEntities = new UserMentionEntity[]{};
        assertFalse(friendShipService.isNoRetweetAndHasMentionFriend(status,userMentionEntities));
    }

    @Test
    public void givenFriendShipServiceWhenGetReTweetAndMentionUsersThenFalse(){
        Status retweetStatus = mock(Status.class);
        when(status.getRetweetedStatus()).thenReturn(retweetStatus);
        UserMentionEntity[] userMentionEntities = {userMentionEntity};
        assertFalse(friendShipService.isNoRetweetAndHasMentionFriend(status,userMentionEntities));
    }

    @Test
    public void givenFriendShipServiceWhenGetReTweetAndNoMentionUsersThenFalse(){
        Status retweetStatus = mock(Status.class);
        when(status.getRetweetedStatus()).thenReturn(retweetStatus);
        UserMentionEntity[] userMentionEntities = new UserMentionEntity[]{};
        assertFalse(friendShipService.isNoRetweetAndHasMentionFriend(status,userMentionEntities));
    }

}
