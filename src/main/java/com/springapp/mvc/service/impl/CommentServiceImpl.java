package com.springapp.mvc.service.impl;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.CommentRatio;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.repository.CommentRepository;
import com.springapp.mvc.service.CommentRatioService;
import com.springapp.mvc.service.CommentService;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentRatioService commentRatioService;

    @Autowired
    private UserService userService;

    @Override
    public void commentRated(CommentRatio commentRatio) {
        commentRatioService.save(commentRatio);
    }

    @Override
    public Boolean isCommentRatedByUser(int userId, int commentId) {
        return commentRatioService.isCommentRatedByUser(userId, commentId);
    }

    @Override
    public int getPositiveRatingOfUser(int userId) {
        int sum = 0;
        for (Comment comment : commentRepository.findAll()) {
            if (comment.getUser().getId() == userId) {
                sum = sum + comment.getPositiveRatio();
            }
        }
        if (sum == 0)
            sum++;
        return sum;
    }

    @Override
    public int getNegativeRatingOfUser(int userId) {
        int sum = 0;
        for (Comment comment : commentRepository.findAll()) {
            if (comment.getUser().getId() == userId) {
                sum = sum + comment.getNegativeRatio();
            }
        }
        if (sum == 0)
            sum++;
        return sum;
    }

    @Override
    public List<Integer> getPositiveVotesOfAllUsers() {
        List<Integer> result = new ArrayList<Integer>();
        for (User user : userService.getAll()) {
            result.add(getPositiveRatingOfUser(user.getId()));
        }
        return result;
    }

    @Override
    public List<Integer> getNegativeVotesOfAllUsers() {
        List<Integer> result = new ArrayList<Integer>();
        for (User user : userService.getAll()) {
            result.add(getNegativeRatingOfUser(user.getId()) * (-1));
        }
        return result;
    }

    @Override
    public void add(Comment comment) {
        commentRepository.save(comment);
    }


    @Override
    public List<Comment> getAllForEnterprise(int enterpriseId) {
        return commentRepository.getAllForEnterprise(enterpriseId);
    }

    @Override
    public void update(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment get(Integer id) {
        return commentRepository.findOne(id);
    }

    @Override
    public void delete(int commentId) {
        commentRepository.delete(commentId);
    }
}
