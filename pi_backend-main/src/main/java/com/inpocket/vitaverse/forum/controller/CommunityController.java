package com.inpocket.vitaverse.forum.controller;

import com.inpocket.vitaverse.forum.entity.Community;
import com.inpocket.vitaverse.forum.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/communities")

public class CommunityController {
    CommunityService communityService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Community createCommunity(@RequestBody Community community){
        return communityService.createCommunity(community);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Community updateCommunity(@RequestBody Community community){
        return communityService.updateCommunity(community);
    }

    @GetMapping("/{communityId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Community> getCommunityById(@PathVariable("communityId") long communityId){
        return communityService.getCommunityById(communityId);
    }

    @DeleteMapping("/delete/{communityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommunity(@PathVariable("communityId") long communityId){
        communityService.deleteCommunity(communityId);
    }
    @GetMapping("/popularCommunities")
    List<Community> getCommunitiesOrderByMembers(){
        return communityService.getCommunitiesOrderByMembers();
    }
    @GetMapping("/newCommunities")
    List<Community> getRecentlyCreatedCommunities(){
        return communityService.getRecentlyCreatedCommunities();
    }

    @GetMapping("/search")
    public Page<Community> searchCommunities(@RequestParam String keyword,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        return communityService.searchCommunities(keyword, page, pageSize);
    }


    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Community createCommunityAndSetCreator(@RequestBody Community community, @PathVariable("userId") long userId) {
        return communityService.createCommunityAndSetCreator(community,userId);
    }

    @GetMapping("/all/{userId}")
    List<Community> findByMembersUserId(@PathVariable("userId") long userId){
        return communityService.findByMembersUserId(userId);
    }


    @PostMapping("/{communityId}/addUser/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    Community addUserToCommunity(@PathVariable("communityId") long communityId, @PathVariable("userId") long userId) {

        return  communityService.addUserToCommunity(communityId, userId);


    }

    @GetMapping("/byCreator/{userId}")
    List<Community> findByCreatorUserId(@PathVariable("userId") long userId){
        return communityService.findByCreatorUserId(userId);
    }



}
