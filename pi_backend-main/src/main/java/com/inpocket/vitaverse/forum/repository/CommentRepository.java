package com.inpocket.vitaverse.forum.repository;
import com.inpocket.vitaverse.forum.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository


public interface CommentRepository extends JpaRepository<Comment, Long>{

List<Comment> findByPostPostId(long post_postId);





}
