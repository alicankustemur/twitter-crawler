package com.colendi.TwitterCrawler.service;

import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface FriendShipCrudService {
    public void saveFriendShip(Status status, UserMentionEntity[] userMentionEntities);
    public void removeAll();
}
