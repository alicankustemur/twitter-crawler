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

    public FriendShipScoringServiceImpl(FriendShipRepository friendShipRepository) {
        this.friendShipRepository = friendShipRepository;
    }

    @Override
    public Map calculateAndGetAllScores() {
        List<FriendShip> friendShipList = friendShipRepository.findAll();
        MultiKeyMap multiKeyMap = new MultiKeyMap();
        multiKeyMap = incrementCountSameMentionFriendsAndAddToMap(multiKeyMap, friendShipList);
        Map<String, Double> scoresMap = new HashMap();
        scoresMap = this.hasNextInMapCalculateScores(scoresMap,multiKeyMap.mapIterator());
        scoresMap = MapUtil.sortStringAndDoubleMapByValue(scoresMap);

        return scoresMap;
    }

    @Override
    public Map hasNextInMapCalculateScores(Map scoresMap,MapIterator mapIterator) {
        while (mapIterator.hasNext()) {
            mapIterator.next();
            scoresMap = this.calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(scoresMap,mapIterator);
        }
        return scoresMap;
    }

    @Override
    public Map calculatePercentAndPutPercentingValueAndMentionFriendNameToMap(Map scoresMap,MapIterator mapIterator) {
        Integer totalCount = friendShipRepository.findAll().size();
        MultiKey multiKey = (MultiKey) mapIterator.getKey();
        Double percent = this.calculatePercent(mapIterator, totalCount);
        String key = multiKey.getKey(1).toString();
        scoresMap.put(key, percent);
        return scoresMap;
    }

    @Override
    public Double calculatePercent(MapIterator mapIterator, Integer totalCount) {
        Double value = Double.valueOf(mapIterator.getValue().toString());
        Double doubleTotalCount = Double.valueOf(totalCount.toString());
        Double percent = (100d * value) / doubleTotalCount;
        percent = this.convertDoubleToThreeDigit(percent);
        return percent;
    }

    @Override
    public Double convertDoubleToThreeDigit(Double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        Double convertedValue = Double.parseDouble(df.format(value));
        return convertedValue;
    }

    @Override
    public MultiKeyMap incrementCountSameMentionFriendsAndAddToMap(MultiKeyMap multiKeyMap, List<FriendShip> friendShipList) {
        for (FriendShip friendShip : friendShipList) {
            multiKeyMap = this.isExistIncreaseCountIfisNotExistPutDirectly(multiKeyMap, friendShip);
        }

        return multiKeyMap;
    }

    @Override
    public MultiKeyMap isExistIncreaseCountIfisNotExistPutDirectly(MultiKeyMap multiKeyMap, FriendShip friendShip) {
        Object value = multiKeyMap.get(friendShip.getUsername(), friendShip.getMentionFriend());
        if (value == null) {
            multiKeyMap.put(friendShip.getUsername(), friendShip.getMentionFriend(), 1);
        } else {
            int count = (Integer) value + 1;
            multiKeyMap.put(friendShip.getUsername(), friendShip.getMentionFriend(), count);
        }

        return multiKeyMap;
    }


}
