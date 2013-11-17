package com.springapp.mvc.utils;

import com.springapp.mvc.entity.Comment;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.CommentService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentHelper {
    static final Logger LOGGER = Logger.getLogger(CommentHelper.class);
    private Comment comment;
    private User user;

    @Autowired
    private CommentService commentService;

    public static String getUserProfilePath() {
        return userProfilePath;
    }

    public static void setUserProfilePath(String userProfilePath) {
        CommentHelper.userProfilePath = userProfilePath;
    }

    private static String userProfilePath;

    public static String getAuthorLinkStyle() {
        return authorLinkStyle;
    }

    private static String authorLinkStyle;

    static {
        userProfilePath = "user/profile";
        authorLinkStyle = "comment_reply_author";
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthor() {
        return String.format("%s %s", comment.getUser().getName(), comment.getUser().getSurname());
    }

    private String getAuthor(User user) {
        return String.format("%s %s", user.getName(), user.getSurname());
    }

    /*public String getMessageToDisplay() {
        String message;
        if (comment.getParentComment() != null) {
            String[] messageParts = comment.getContent().split(",");
            message = String.format("<a class=\"%s\" href=\"/%s/%s\">%s</a>%s",
                    getAuthorLinkStyle(),
                    getUserProfilePath(),
                    messageParts[0],
                    messageParts[0],
                    messageParts[1]);
        } else {
            message = comment.getContent();
        }
        return message;
    }*/

    public String convertCommentsToJson(List<Comment> comments, User authUser) {
        JSONArray jsonArray = new JSONArray();
       /* LinkedList<String> resultList = new LinkedList<String>();*/
        for (Comment comment : comments) {
            jsonArray.put(convertCommentToJson(comment, authUser));
        }
        return jsonArray.toString();
    }

    public String convertCommentToJson(Comment comment, User authUser) {
        JSONObject jsonObject = new JSONObject();
        try {
            boolean authenticated = authUser != null;
            boolean owner = authenticated && authUser.getId().equals(comment.getUser().getId());
            jsonObject.put("id", comment.getId());
            jsonObject.put("avatar_src",
                    "https://lh4.ggpht.com/B2UjZIJ0iwp0LfkUnITqp_"
                            + "iYek9PWEXuXLOHl3XYPMR_2zEGsHPR6ruu4OeQKOfvJnJ8=w300");
            jsonObject.put("isAnswer", comment.getParentComment() != null);
            if (comment.getParentComment() != null) {
                jsonObject.put("answerFor", comment.getParentComment().getUser().getName());
                String[] appeal = getAppealUserName(comment);
                if (appeal != null) {
                    jsonObject.put("appealUserName", appeal[0]);
                    jsonObject.put("appealContent", appeal[1]);
                    jsonObject.put("appealProfileLink", "/user/profile/"
                                    + comment.getParentComment().getUser().getNickname());
                }
            }
            jsonObject.put("content", comment.getContent());
            jsonObject.put("ratio_positive", comment.getPositiveRatio());
            jsonObject.put("ratio_negative", comment.getNegativeRatio());
            jsonObject.put("owner", owner);
            jsonObject.put("profile_link", "/user/profile/" + comment.getUser().getNickname());
            jsonObject.put("author_name", getAuthor(comment.getUser()));
            jsonObject.put("date", comment.getDate());
            if (comment.getEditDate() != null) {
                jsonObject.put("edit_date", comment.getEditDate());
            }
            jsonObject.put("can_rate", authenticated && !commentService.isCommentRatedByUser(authUser.getId(),
                    comment.getId()));
            jsonObject.put("can_reply", !owner && authenticated);
        } catch (JSONException e) {
            LOGGER.error("Cannot convert to json, message:= " + e.getLocalizedMessage());
        }
        return jsonObject.toString();
    }

    private String[] getAppealUserName(Comment comment) {
        String[] commentParts = comment.getContent().split(",");
        if (commentParts.length > 1 && comment.getParentComment().getUser().getName().equals(commentParts[0])) {
            return commentParts;
        } else {
            return null;
        }
    }
}
