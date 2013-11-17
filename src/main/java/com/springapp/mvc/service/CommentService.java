package com.springapp.mvc.service;

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.CommentRatio;

import java.util.List;

public interface CommentService {
    void commentRated(CommentRatio commentRatio);

    Boolean isCommentRatedByUser(int userId, int commentId);

    int getPositiveRatingOfUser(int userId);

    int getNegativeRatingOfUser(int userId);

    List<Integer> getPositiveVotesOfAllUsers();

    List<Integer> getNegativeVotesOfAllUsers();

    void add(Comment comment);

    List<Comment> getAllForEnterprise(int enterpriseId);

    void update(Comment comment);

    Comment get(Integer id);

    void delete(int commentId);

    List<Integer> getLastHours();

    List<Integer> getLastCommentsOfUser(int userId);
}
