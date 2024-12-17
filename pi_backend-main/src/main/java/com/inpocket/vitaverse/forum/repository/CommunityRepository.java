package com.inpocket.vitaverse.forum.repository;

import com.inpocket.vitaverse.forum.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findTop5ByOrderByMembersDesc();
    List<Community> findTop5ByOrderByCreatedAtDesc();
    Page<Community> findByCommunityNameContainingIgnoreCase(String keyword, Pageable pageable);

    List<Community> findByMembersUserId(Long members_userId);
    List<Community> findByCreatorUserId(Long creator_userId);



}
