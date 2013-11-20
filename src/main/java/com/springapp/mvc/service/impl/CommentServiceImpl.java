package com.springapp.mvc.service.impl;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    public static final int LAST_COMMENTS_TIME_ARRAY = 12;

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
        if (sum == 0) {
            sum++;
        }
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
        if (sum == 0) {
            sum++;
        }
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

    @Override
    public List<Integer> getLastHours() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < LAST_COMMENTS_TIME_ARRAY; i++) {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, i * (-1));
            result.add(cal.get(Calendar.HOUR_OF_DAY));
        }
        return result;
    }


    @Override
    public List<Integer> getLastCommentsOfUser(int userId) {
        List<Integer> result = new ArrayList<Integer>();
        List<Comment> allComments = commentRepository.findAll();
        /*for (Comment comment : allComments) {
            if (comment.getUser().getId() != userId) {
                allComments.remove(comment);
                System.out.println("Comment removed");
            }
        }*/
        for (int i = 0; i < LAST_COMMENTS_TIME_ARRAY; i++) {
            int sum = 0;
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, i * (-1));
            Date max = cal.getTime();
            System.out.println("Maximum approved");
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, i * (-2));
            Date min = cal.getTime();
            System.out.println("Minimum approved");

            for (Comment comment : allComments) {
                if (comment.getUser().getId() == userId) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
//                Date commentDate = null;
                    System.out.println("Time approved");
                    try {
                    Date commentDate = formatter.parse(comment.getDate());
                    if (min.compareTo(commentDate) * commentDate.compareTo(max) > 0) {
                        sum++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                }
            }
            result.add(sum);
            System.out.println("Sum: " + sum);
        }
        return result;
    }
}
