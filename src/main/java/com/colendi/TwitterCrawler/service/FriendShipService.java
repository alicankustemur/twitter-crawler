package com.colendi.TwitterCrawler.service;

import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Created by alicankustemur on 30/10/2017.
 */
public interface FriendShipService extends FriendShipSaveService {
    public void saveUserAndMentionUserAndTweetId(Status status, UserMentionEntity[] userMentionEntities);
    public boolean isNoRetweetAndHasMentionFriend(Status status, UserMentionEntity[] userMentionEntities);
}
