package com.springapp.mvc.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "comment_ratio")
public class CommentRatio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @Column(name = "VALUE", columnDefinition = "BIT", nullable = true)
    private Boolean value;

    /*Getters and Setters*/
    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public Comment getComment() {
        return comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CommentRatio) {
            final CommentRatio other = (CommentRatio) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else {
            return false;
        }
    }
}
