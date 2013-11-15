package com.springapp.mvc.repository;

import com.springapp.mvc.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c from Comment c where c.enterprise.id = ?1")
    public List<Comment> getAllForEnterprise(int enterpriseId);
}
