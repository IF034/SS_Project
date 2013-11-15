package com.springapp.mvc.web;

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.CommentRatio;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.CommentService;
import com.springapp.mvc.service.EnterpriseService;
import com.springapp.mvc.service.UserService;
import com.springapp.mvc.utils.CommentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentHelper commentHelper;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private UserService userService;

    private User authUser;

    @RequestMapping(value = "/getComments",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = "application/json")
    @ResponseBody
    public String getComments(@RequestParam("id") int enterpriseId) {
        String jsonStrings = commentHelper.convertCommentsToJson(enterpriseService.getComments(enterpriseId), getAuthUser());
        return jsonStrings;
    }

    @RequestMapping(value = "/getComment", method = RequestMethod.GET)
    @ResponseBody
    public String getCommentToEdit(@RequestParam("id") int commentId) {
        return commentHelper.convertCommentToJson(enterpriseService.getComment(commentId), getAuthUser());
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
    @ResponseBody
    public String deleteComment(@RequestParam("id") int commentId) {
        commentService.delete(commentId);
        return "ok";
    }


    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam("enterprise_id") int enterpriseId,
                      @RequestParam("content") String commentContent) {
        Comment commentToAdd = new Comment();
        commentToAdd.setContent(commentContent);
        commentToAdd.setUser(getAuthUser());
        commentToAdd.setEnterprise(enterpriseService.get(enterpriseId));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        commentToAdd.setDate(dateFormat.format(new Date()));
        commentService.add(commentToAdd);
        return commentHelper.convertCommentToJson(enterpriseService.getComment(commentToAdd.getId()), getAuthUser());
    }

    @RequestMapping(value = "/rateComment", method = RequestMethod.POST)
    @ResponseBody
    public String rateComment(@RequestParam("action") String action,
                       @RequestParam("id") int commentId) {
        Comment commentToUpdate = commentService.get(commentId);
        rateComment(commentToUpdate, action);
        commentService.update(commentToUpdate);
        return commentHelper.convertCommentToJson(enterpriseService.getComment(commentId), getAuthUser());
    }

    @RequestMapping(value = "/updateComment", method = RequestMethod.POST)
    @ResponseBody
    public String updateComment(@RequestParam("id") int commentId,
                         @RequestParam("content") String commentContent) {
        Comment commentToUpdate = commentService.get(commentId);
        commentToUpdate.setContent(commentContent);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        commentToUpdate.setEditDate(dateFormat.format(new Date()));
        commentService.update(commentToUpdate);
        return commentHelper.convertCommentToJson(enterpriseService.getComment(commentId), getAuthUser());
    }


    private String mergeMessageParts(String[] messageParts) {
        StringBuilder resultMessage = new StringBuilder();
        for (int iterator = 1; iterator < messageParts.length; iterator++) {
            resultMessage.append(messageParts[iterator]);
            if (messageParts.length - 1 != iterator) {
                resultMessage.append(",");
            }
        }
        return resultMessage.toString();
    }

    public User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return userService.getUser(auth.getName());
        }
        return null;
    }

    private void rateComment(Comment commentToUpdate, String action) {
        CommentRatio commentRatio = new CommentRatio();
        commentRatio.setComment(commentToUpdate);
        commentRatio.setUser(getAuthUser());
        if (action.equals("up")) {
            commentToUpdate.setPositiveRatio(commentToUpdate.getPositiveRatio() + 1);
            commentRatio.setValue(true);
            commentService.commentRated(commentRatio);
        } else if (action.equals("down")) {
            commentRatio.setValue(false);
            commentToUpdate.setNegativeRatio(commentToUpdate.getNegativeRatio() + 1);
            commentService.commentRated(commentRatio);
        }
    }
}

