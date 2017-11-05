package com.colendi.TwitterCrawler.service;

import com.colendi.TwitterCrawler.domain.FriendShip;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface FriendShipCrudService {
    public void saveFriendShipList(Status status, UserMentionEntity[] userMentionEntities);
    public FriendShip saveFriendShip(Status status, UserMentionEntity userMentionEntity);
    public void removeAll();
}
