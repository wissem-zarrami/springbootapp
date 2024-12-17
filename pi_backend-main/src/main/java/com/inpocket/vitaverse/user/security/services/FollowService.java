package com.inpocket.vitaverse.user.security.services;

import com.inpocket.vitaverse.user.entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowService {
  Follow saveFollow(Follow follow);
    Optional<Follow> getFollowById(Long id);
    List<Follow> getAllFollows();
}
