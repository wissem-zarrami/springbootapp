package com.inpocket.vitaverse.forum.repository;
import com.inpocket.vitaverse.forum.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>{
    Optional<CommentLike>  findByUserUserIdAndComment_CommentId(Long user_userId, long comment_commentId);
    List<CommentLike>  findByComment_CommentId(long comment_commentId);

}
