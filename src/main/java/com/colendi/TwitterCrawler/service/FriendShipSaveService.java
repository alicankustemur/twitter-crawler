package com.colendi.TwitterCrawler.service;

import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface FriendShipSaveService  {
    public void saveFriendShip(Status status, UserMentionEntity[] userMentionEntities);
}
