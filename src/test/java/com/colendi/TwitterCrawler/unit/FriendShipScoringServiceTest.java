package com.colendi.TwitterCrawler.unit;

import com.colendi.TwitterCrawler.domain.FriendShip;
import com.colendi.TwitterCrawler.repository.FriendShipRepository;
import com.colendi.TwitterCrawler.service.FriendShipScoringService;
import com.colendi.TwitterCrawler.service.FriendShipScoringServiceImpl;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by alicankustemur on 02/11/2017.
 */
@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class FriendShipScoringServiceTest {

    @Mock
    private FriendShipRepository friendShipRepository;

    @InjectMocks
    private FriendShipScoringServiceImpl friendShipScoringService;

    @Test
    public void givenFriendShipScoringServiceWhenDoubleValueThenReturnThreeDigitDoubleValue() throws Exception {
        Double value = 1.23456789d;
        Double threeDigitValue = 1.23d;

        Double convertedValue = friendShipScoringService.convertDoubleToThreeDigit(value);
        assertEquals(threeDigitValue, convertedValue);
    }

    @Test
    public void givenFriendShipScoringServiceWhenMapIteratorAndTotalCountWhenCalculatedPercent() throws Exception {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap.put("cmylmz", "canyilmaz", 10);
        multiKeyMap.put("cmylmz", "ozanguven", 6);
        multiKeyMap.put("cmylmz", "zaferalgoz", 7);

        Double firstExpected = 26.09; // zaferalgoz
        Double secondExpected = 30.43;// ozanguven
        Double thirdExpected = 43.48; // canyilmaz

        MapIterator mapIterator = multiKeyMap.mapIterator();
        mapIterator.next();
        Integer totalCount = 23;

        assertEquals(firstExpected, friendShipScoringService.calculatePercent(mapIterator, totalCount));

        mapIterator.next();
        assertEquals(secondExpected, friendShipScoringService.calculatePercent(mapIterator, totalCount));

        mapIterator.next();
        assertEquals(thirdExpected, friendShipScoringService.calculatePercent(mapIterator, totalCount));

    }

    @Test
    public void givenFriendShipScoringServiceWhenMapAndMapIteratorReturnCalculatePercentAndPutToMap() throws Exception {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap.put("cmylmz", "canyilmaz", 3);
        multiKeyMap.put("cmylmz", "ozanguven", 2);
        multiKeyMap.put("cmylmz", "zaferalgoz", 1);


        when(friendShipRepository.findAll()).thenReturn(this.createFriendShipList());

        Map<String, Double> scoresMap = new HashMap();
        MapIterator mapIterator = multiKeyMap.mapIterator();
        mapIterator.next();

        friendShipScoringService.calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(scoresMap, mapIterator);
        mapIterator.next();

        friendShipScoringService.calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(scoresMap, mapIterator);
        mapIterator.next();

        scoresMap = friendShipScoringService.calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(scoresMap, mapIterator);

        Double firstExpected = 50.0d;
        assertEquals(scoresMap.get("canyilmaz"), firstExpected);

        Double secondExpected = 33.33d;
        assertEquals(scoresMap.get("ozanguven"), secondExpected);

        Double thirdExpected = 16.67d;
        assertEquals(scoresMap.get("zaferalgoz"), thirdExpected);
    }

    @Test
    public void givenFriendShipScoringServiceWhenMultiKeyMapAndOneFriendShipThenReturnPutThisFriendShip() throws Exception {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        FriendShip friendShip = new FriendShip("1", "cmylmz", 1L, "canyilmaz");
        FriendShip friendShipAnother = new FriendShip("2", "cmylmz", 2L, "ozanguven");

        multiKeyMap = friendShipScoringService.isExistIncreaseCountIfisNotExistPutDirectly(multiKeyMap, friendShip);
        multiKeyMap = friendShipScoringService.isExistIncreaseCountIfisNotExistPutDirectly(multiKeyMap, friendShipAnother);

        assertEquals(1, multiKeyMap.get(friendShip.getUsername(), friendShip.getMentionFriend()));
        assertEquals(1, multiKeyMap.get(friendShipAnother.getUsername(), friendShipAnother.getMentionFriend()));
        assertEquals(2, multiKeyMap.size());
    }

    @Test
    public void givenFriendShipScoringServiceWhenMultiKeyMapAndOneFriendShipThenReturnPutThisFriendShipIfExistIncrementCount() throws Exception {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap.put("cmylmz", "canyilmaz", 1);

        FriendShip friendShip = new FriendShip("1", "cmylmz", 1L, "canyilmaz");

        multiKeyMap = friendShipScoringService.isExistIncreaseCountIfisNotExistPutDirectly(multiKeyMap, friendShip);

        assertEquals(2, multiKeyMap.get(friendShip.getUsername(), friendShip.getMentionFriend()));
        assertEquals(1, multiKeyMap.size());
    }

    @Test
    public void givenFriendShipScoringServiceWhenFriendShipListThenReturnScoredMap() {
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap.put("cmylmz", "canyilmaz", 3);
        multiKeyMap.put("cmylmz", "ozanguven", 2);
        multiKeyMap.put("cmylmz", "zaferalgoz", 1);

        when(friendShipRepository.findAll()).thenReturn(createFriendShipList());

        Map scoresMap = friendShipScoringService.calculateAndGetAllScores();

        Double firstExpected = 50.0d;
        assertEquals(scoresMap.get("canyilmaz"), firstExpected);

        Double secondExpected = 33.33d;
        assertEquals(scoresMap.get("ozanguven"), secondExpected);

        Double thirdExpected = 16.67d;
        assertEquals(scoresMap.get("zaferalgoz"), thirdExpected);

    }


    private List<FriendShip> createFriendShipList() {
        List<FriendShip> friendShipList = new LinkedList<>();

        friendShipList.add(new FriendShip("1", "cmylmz", 1L, "canyilmaz"));
        friendShipList.add(new FriendShip("2", "cmylmz", 2L, "canyilmaz"));
        friendShipList.add(new FriendShip("3", "cmylmz", 3L, "canyilmaz"));

        friendShipList.add(new FriendShip("4", "cmylmz", 4L, "ozanguven"));
        friendShipList.add(new FriendShip("5", "cmylmz", 5L, "ozanguven"));

        friendShipList.add(new FriendShip("6", "cmylmz", 6L, "zaferalgoz"));

        return friendShipList;
    }



}
