package com.springapp.mvc.repository;

import com.springapp.mvc.entity.CommentRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRatioRepository extends JpaRepository<CommentRatio, Integer> {

    @Query("select case when (count(cr) > 0)  then true else false end "
            + "from CommentRatio cr where cr.user.id = ?1 and cr.comment.id = ?2 ")
    Boolean isCommentRatedByUser(int userId, int commentId);
}
