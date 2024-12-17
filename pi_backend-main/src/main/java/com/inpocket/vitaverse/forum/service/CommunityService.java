package com.inpocket.vitaverse.forum.service;


import com.inpocket.vitaverse.forum.entity.Community;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommunityService {
    Community createCommunity(Community community);
    Community updateCommunity(Community community);
    Optional<Community> getCommunityById(long communityId);
    void deleteCommunity(long communityId);
    List<Community> getAllCommunities( );
    List<Community> getCommunitiesOrderByMembers();
    List<Community> getRecentlyCreatedCommunities();
    Page<Community> searchCommunities(String keyword, int page, int pageSize);

    Community createCommunityAndSetCreator(Community community, Long userId);

    List<Community> findByMembersUserId(Long members_userId);

    Community addUserToCommunity(Long communityId, Long userId) ;

    List<Community> findByCreatorUserId(Long creator_userId);




}
