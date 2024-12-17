package com.inpocket.vitaverse.user.controllers;
import com.inpocket.vitaverse.user.entity.Follow;
import com.inpocket.vitaverse.user.security.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/follows")
    public class FollowController {
        @Autowired

     FollowService followService;

        @Autowired
        public FollowController(FollowService followService) {
            this.followService = followService;
        }

        @PostMapping
        public Follow createFollow(@RequestBody Follow follow) {
            return followService.saveFollow(follow);
        }

        @GetMapping("/{id}")
        public Optional<Follow> getFollowById(@PathVariable Long id) {
            return followService.getFollowById(id);
        }

        @GetMapping
        public List<Follow> getAllFollows() {
            return followService.getAllFollows();
        }
    }


