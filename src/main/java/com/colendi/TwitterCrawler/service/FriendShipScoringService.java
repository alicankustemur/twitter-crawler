package com.colendi.TwitterCrawler.service;

import com.colendi.TwitterCrawler.domain.FriendShip;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.List;
import java.util.Map;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface FriendShipScoringService {
    public Map calculateAndGetAllScores();
    public Map hasNextInMapCalculateScores(Map scoresMap,MapIterator mapIterator);
    public Map calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(Map scoresMap,MapIterator mapIterator);
    public Double calculatePercent(MapIterator mapIterator, Integer totalCount);
    public Double convertDoubleToThreeDigit(Double value);
    public MultiKeyMap incrementCountSameMentionFriendsAndAddToMap(MultiKeyMap multiKeyMap, List<FriendShip> friendShipList);
    public MultiKeyMap isExistIncreaseCountIfisNotExistPutDirectly(MultiKeyMap multiKeyMap, FriendShip friendShip);
}
