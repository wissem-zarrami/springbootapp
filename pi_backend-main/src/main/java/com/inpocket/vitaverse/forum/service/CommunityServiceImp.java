package com.inpocket.vitaverse.forum.service;

import com.inpocket.vitaverse.forum.entity.Community;
import com.inpocket.vitaverse.forum.repository.CommunityRepository;
import com.inpocket.vitaverse.user.entity.User;
import com.inpocket.vitaverse.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class CommunityServiceImp implements CommunityService{
    CommunityRepository communityRepository;
    UserRepository userRepository;
    @Override
    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Community updateCommunity(Community community) {
        return communityRepository.save(community);
    }

    @Override
    public Optional<Community> getCommunityById(long communityId) {
        return communityRepository.findById(communityId);
    }

    @Override
    public void deleteCommunity(long communityId) {
        communityRepository.deleteById(communityId);
    }

    @Override
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Override //get the communities ordered by te number of members
    public List<Community> getCommunitiesOrderByMembers() {
        return communityRepository.findTop5ByOrderByMembersDesc();
    }

    @Override // get the communities new ones first
    public List<Community> getRecentlyCreatedCommunities() {
        return communityRepository.findTop5ByOrderByCreatedAtDesc();
    }
    @Override
    public Page<Community> searchCommunities(String keyword, int page, int pageSize) {
        return communityRepository.findByCommunityNameContainingIgnoreCase(keyword, PageRequest.of(page, pageSize));
    }

    // Assuming this method is part of CommunityServiceImp class

    @Override
    public Community createCommunityAndSetCreator(Community community, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Initialize the members set if it's null
            if (community.getMembers() == null) {
                community.setMembers(new HashSet<>());
            }

            // Add the creator to the members
            community.getMembers().add(user);

            // Set the creator of the community
            community.setCreator(user);

            // Save the community
            return communityRepository.save(community);
        } catch (Exception e) {
            throw new RuntimeException("Error creating community and setting user", e);
        }
    }

    @Override
    public List<Community> findByMembersUserId(Long members_userId) {
        return communityRepository.findByMembersUserId(members_userId);
    }

    @Override
    public Community addUserToCommunity(Long communityId, Long userId) {
        try {
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(() -> new RuntimeException("Community not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Initialize the members set if it's null
            if (community.getMembers() == null) {
                community.setMembers(new HashSet<>());
            }

            // Add the user to the members
            community.getMembers().add(user);

            // Save the updated community
            return communityRepository.save(community);
        } catch (Exception e) {
            throw new RuntimeException("Error adding user to community", e);
        }
    }

    @Override
    public List<Community> findByCreatorUserId(Long creator_userId) {
        return communityRepository.findByCreatorUserId(creator_userId);
    }


}
