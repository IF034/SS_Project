package com.springapp.mvc.service.impl;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import com.springapp.mvc.entity.CommentRatio;
import com.springapp.mvc.repository.CommentRatioRepository;
import com.springapp.mvc.service.CommentRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentRatioServiceImpl implements CommentRatioService {

    @Autowired
    private CommentRatioRepository commentRatioRepository;

    @Override
    public void save(CommentRatio commentRatio) {
        commentRatioRepository.save(commentRatio);
    }

    @Override
    public Boolean isCommentRatedByUser(int userId, int commentId) {
       return commentRatioRepository.isCommentRatedByUser(userId, commentId);
    }
}
