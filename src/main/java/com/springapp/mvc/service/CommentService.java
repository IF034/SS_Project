package com.springapp.mvc.service;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.CommentRatio;

import java.util.List;

public interface CommentService {
    public void commentRated(CommentRatio commentRatio);
    public Boolean isCommentRatedByUser(int userId, int commentId);
    public int getPositiveRatingOfUser(int userId);
    public int getNegativeRatingOfUser(int userId);
    public List<Integer> getPositiveVotesOfAllUsers();
    public List<Integer> getNegativeVotesOfAllUsers();
    public void add(Comment comment);
    public List<Comment> getAllForEnterprise(int enterpriseId);
    public void update(Comment comment);
    public Comment get(Integer id);
    public void delete(int commentId);
}
