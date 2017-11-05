package com.colendi.TwitterCrawler.unit;

import com.colendi.TwitterCrawler.domain.FriendShip;
import com.colendi.TwitterCrawler.repository.FriendShipRepository;
import com.colendi.TwitterCrawler.service.FriendShipCrudService;
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
import twitter4j.Friendship;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserMentionEntity;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by alicankustemur on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class FriendShipCrudServiceTest {

    @Mock
    private FriendShipRepository friendShipRepository;

    @Mock
    private UserMentionEntity userMentionEntity;

    @Mock
    private Status status;

    @Mock
    private User user;

    @InjectMocks
    private FriendShipServiceImpl friendShipCrudService;


    @Test
    public void givenFriendShipCrudServiceWhenGetTweetAndMentionUsersThenSaveThisFriendShip(){
        String username = "alican";
        String mentionFriend = "veli";
        Long tweetId = 1234L;

        when(userMentionEntity.getScreenName()).thenReturn(mentionFriend);
        when(status.getId()).thenReturn(tweetId);
        when(status.getUser()).thenReturn(user);
        when(status.getUser().getScreenName()).thenReturn(username);

        FriendShip friendShip = new FriendShip();
        friendShip.setUsername(username);
        friendShip.setTweetId(tweetId);
        friendShip.setMentionFriend(mentionFriend);

        when(friendShipRepository.save(friendShip)).thenReturn(friendShip);

        FriendShip gettingFriendShip = friendShipCrudService.saveFriendShip(status,userMentionEntity);
        assertThat(gettingFriendShip.getTweetId(), is(equalTo(friendShip.getTweetId())));
    }

    @Test
    public void givenFriendShipCrudServiceWhenCallDeleteAllThenRemoveAllFriendShips(){
        List<FriendShip> friendShipList = new LinkedList<>();
        friendShipList.add(new FriendShip());
        when(friendShipRepository.findAll()).thenReturn(friendShipList);

        assertNotEquals(0,friendShipRepository.findAll().size());

        friendShipCrudService.removeAll();
        when(friendShipRepository.findAll()).thenReturn(new LinkedList<>());

        assertEquals(0,friendShipRepository.findAll().size());
    }


}
