package com.colendi.TwitterCrawler.service;

import com.colendi.TwitterCrawler.domain.FriendShip;
import com.colendi.TwitterCrawler.repository.FriendShipRepository;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * Created by alicankustemur on 30/10/2017.
 */
@Service
public class FriendShipServiceImpl implements FriendShipService {

    private final FriendShipRepository friendShipRepository;

    public FriendShipServiceImpl(FriendShipRepository friendShipRepository) {
        this.friendShipRepository = friendShipRepository;
    }

    @Override
    public void saveUserAndMentionUserAndTweetId(Status status, UserMentionEntity[] mentionEntities) {
        for(UserMentionEntity userMentionEntity : mentionEntities){
            FriendShip friendShip = new FriendShip();
            friendShip.setMentionFriend(userMentionEntity.getScreenName());
            friendShip.setTweetId(status.getId());
            friendShip.setUsername(status.getUser().getScreenName());
            friendShipRepository.save(friendShip);
        }
    }

    @Override
    public boolean isNoRetweetAndHasMentionFriend(Status status, UserMentionEntity[] userMentionEntities) {
        if (status.getRetweetedStatus() == null && userMentionEntities.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void saveFriendShip(Status status, UserMentionEntity[] userMentionEntities) {
        if(isNoRetweetAndHasMentionFriend(status,userMentionEntities)){
            saveUserAndMentionUserAndTweetId(status,userMentionEntities);
        }
    }
}
