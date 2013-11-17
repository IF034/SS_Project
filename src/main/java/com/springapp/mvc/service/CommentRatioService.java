package com.springapp.mvc.service;

import com.springapp.mvc.entity.CommentRatio;

public interface CommentRatioService {

    void save(CommentRatio commentRatio);

    Boolean isCommentRatedByUser(int userId, int commentId);
}
