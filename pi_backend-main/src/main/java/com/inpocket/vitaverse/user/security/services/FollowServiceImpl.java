package com.inpocket.vitaverse.user.security.services;

import com.inpocket.vitaverse.user.entity.Follow;
import com.inpocket.vitaverse.user.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
  FollowRepository followRepository;




    public Follow saveFollow(Follow follow) {
        return followRepository.save(follow);
    }

    public Optional<Follow> getFollowById(Long id) {
        return followRepository.findById(id);
    }

    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }
}
