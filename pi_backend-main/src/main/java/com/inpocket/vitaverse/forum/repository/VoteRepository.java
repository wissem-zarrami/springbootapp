package com.inpocket.vitaverse.forum.repository;
import com.inpocket.vitaverse.forum.entity.Vote;
import com.inpocket.vitaverse.forum.entity.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
    Optional<Vote> findByUserUserIdAndPostPostId(Long user_userId, long post_postId);

    Vote findByPostPostIdAndUserUserId(long post_postId, Long user_userId);

    List<Vote> findByPostPostIdAndVoteType(long post_postId, VoteType voteType);
    List<Vote> findByPostPostId(long post_postId);
}
