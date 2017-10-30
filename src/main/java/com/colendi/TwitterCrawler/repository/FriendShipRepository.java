package com.colendi.TwitterCrawler.repository;

import com.colendi.TwitterCrawler.domain.FriendShip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendShipRepository extends MongoRepository<FriendShip, String>
{
    public FriendShip findByTweetId(Long id);
}
