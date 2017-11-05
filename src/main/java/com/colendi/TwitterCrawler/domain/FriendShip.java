package com.colendi.TwitterCrawler.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "friendship")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendShip {

    @Id
    private String id;

    private String username;
    private Long tweetId;
    private String mentionFriend;
}
