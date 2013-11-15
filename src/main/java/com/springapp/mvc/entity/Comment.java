package com.springapp.mvc.entity;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "COMMENT")
public class Comment {
    public static final int MAX_LENGTH_OF_COMMENT_IN_DB = 800;
    public static final int MAX_LENGTH_OF_DATE_IN_DB = 30;
    public static final int MAX_LENGTH_OF_EDITDATE_IN_DB = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "POSITIVE_RATIO", nullable = false)
    private Integer positiveRatio;

    @Column(name = "NEGATIVE_RATIO", nullable = false)
    private Integer negativeRatio;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @Lob
    @Column(name = "CONTENT", nullable = false, length = MAX_LENGTH_OF_COMMENT_IN_DB)
    private String content;

    @Column(name = "DATE", nullable = false, length = MAX_LENGTH_OF_DATE_IN_DB)
    private String date;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CommentRatio> commentRatios;

    @OneToOne(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment parentComment;

    @Column(name = "EDIT_DATE", nullable = true, length = MAX_LENGTH_OF_EDITDATE_IN_DB)
    private String editDate;


    public Comment() {
        setPositiveRatio(0);
        setNegativeRatio(0);
    }

    /*Getters and Setters*/
    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setCommentRatios(Set<CommentRatio> commentRatios) {
        this.commentRatios = commentRatios;
    }

    public Set<CommentRatio> getCommentRatios() {
        return commentRatios;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositiveRatio() {
        return positiveRatio;
    }

    public void setPositiveRatio(Integer positiveRatio) {
        this.positiveRatio = positiveRatio;
    }

    public Integer getNegativeRatio() {
        return negativeRatio;
    }

    public void setNegativeRatio(Integer negativeRatio) {
        this.negativeRatio = negativeRatio;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof Comment){
            final Comment other = (Comment) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else{
            return false;
        }
    }
}
