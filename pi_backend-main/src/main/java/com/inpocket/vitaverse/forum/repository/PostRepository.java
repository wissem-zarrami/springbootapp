package com.inpocket.vitaverse.forum.repository;
import com.inpocket.vitaverse.forum.entity.Community;
import com.inpocket.vitaverse.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

List<Post> findByCommunityCommunityIdOrderByCreatedAtDesc(long community_communityId);

List<Post> findTop50ByOrderByVotesDesc();

List<Post> findByTextContentContainingOrCommunityCommunityNameContainingOrUserFirstNameContaining(String textContent, String community_communityName, String user_firstName);
    List<Post> findByTextContentContaining(String textContent);
    List<Post> findByCommunityCommunityNameContaining(String community_communityName);
    List<Post> findByUserFirstNameContaining(String user_firstName);



}
