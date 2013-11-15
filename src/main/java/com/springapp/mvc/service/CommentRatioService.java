package com.springapp.mvc.service;// Created with IntelliJ IDEA by Yaroslav Kovbas (Xardas)

import com.springapp.mvc.entity.CommentRatio;

public interface CommentRatioService {

    public void save(CommentRatio commentRatio);
    public Boolean isCommentRatedByUser(int userId, int commentId);
}
