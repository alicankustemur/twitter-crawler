package com.colendi.TwitterCrawler.service;

import com.colendi.TwitterCrawler.domain.FriendShip;
import com.colendi.TwitterCrawler.repository.FriendShipRepository;
import com.colendi.TwitterCrawler.util.MapUtil;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alicankustemur on 30/10/2017.
 */
@Service
public class FriendShipScoringServiceImpl implements FriendShipScoringService {

    private final FriendShipRepository friendShipRepository;
    private MultiKeyMap multiKeyMap;
    private Integer totalCount;
    private Map<String,Double> scoresMap;

    public FriendShipScoringServiceImpl(FriendShipRepository friendShipRepository) {
        this.friendShipRepository = friendShipRepository;
    }

    @Override
    public Map calculateAndGetAllScores() {
        List<FriendShip> friendShipList = friendShipRepository.findAll();
        incrementCountSameMentionFriendsAndAddToMap(friendShipList);
        this.hasNextInMapCalculateScores(multiKeyMap.mapIterator());

        return MapUtil.sortStringAndDoubleMapByValue(scoresMap);
    }

    public void hasNextInMapCalculateScores(MapIterator mapIterator){
        scoresMap = new HashMap();
        while (mapIterator.hasNext()){
            mapIterator.next();
            this.calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(mapIterator);
        }
    }

    private void calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(MapIterator mapIterator){
        MultiKey multiKey = (MultiKey) mapIterator.getKey();
        Double percent = this.calculatePercent(mapIterator,totalCount);
        String key = multiKey.getKey(1).toString();
        scoresMap.put(key,percent);
    }

    private Double calculatePercent(MapIterator mapIterator, Integer totalCount){
        Double value = Double.valueOf(mapIterator.getValue().toString());
        Double doubleTotalCount = Double.valueOf(totalCount.toString());
        Double percent = (100d * value) / doubleTotalCount;
        percent = this.convertDoubleToThreeDigit(percent);
        return percent;
    }

    private Double convertDoubleToThreeDigit(Double value){
        DecimalFormat df = new DecimalFormat("#.##");
        Double convertedValue = Double.parseDouble(df.format(value));
        return convertedValue;
    }

    private void incrementCountSameMentionFriendsAndAddToMap(List<FriendShip> friendShipList){
        totalCount = friendShipList.size();
        multiKeyMap = new MultiKeyMap();
        for (FriendShip friendShip : friendShipList) {
            this.isExistIncreaseCountIfisNotExistPutDirectly(friendShip);
        }
    }

    private void isExistIncreaseCountIfisNotExistPutDirectly(FriendShip friendShip){
        Object value = multiKeyMap.get(friendShip.getUsername(), friendShip.getMentionFriend());
        if (value == null) {
            multiKeyMap.put(friendShip.getUsername(), friendShip.getMentionFriend(), 1);
        } else {
            int count = (Integer) value + 1;
            multiKeyMap.put(friendShip.getUsername(), friendShip.getMentionFriend(), count);
        }
    }





}
