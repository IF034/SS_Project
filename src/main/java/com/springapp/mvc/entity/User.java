package com.springapp.mvc.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    public static final int MAX_LENGTH_OF_FIELD_IN_DB = 30;
    public static final int MAX_LENGTH_OF_NAME = 30;
    public static final int MIN_LENGTH_OF_NAME = 3;
    public static final int MAX_LENGTH_OF_SURNAME = 30;
    public static final int MIN_LENGTH_OF_SURNAME = 3;
    public static final int MAX_LENGTH_OF_NICKNAME = 30;
    public static final int MIN_LENGTH_OF_NICKNAME = 3;
    public static final int MIN_LENGTH_OF_PASSWORD = 6;
    public static final int MAX_LENGTH_OF_EMAIL_IN_DB = 32;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = MAX_LENGTH_OF_FIELD_IN_DB)
    @Pattern(regexp = "^[а-яА-ЯІіЇїЄєa-zA-Z0-9]+$",
            message = "Name must be alpha numeric with no spaces")
    @Size(min = MIN_LENGTH_OF_NAME, max = MAX_LENGTH_OF_NAME,
          message = "Name must be between 3 and 30 characters long.")
    private String name;

    @Column(name = "SURNAME", nullable = false, length = MAX_LENGTH_OF_FIELD_IN_DB)
    @Pattern(regexp = "^[а-яА-ЯІіЇїЄєa-zA-Z0-9]+$",
            message = "Surname must be alpha numeric with no spaces")
    @Size(min = MIN_LENGTH_OF_SURNAME, max = MAX_LENGTH_OF_SURNAME,
          message = "Surname must be between 3 and 30 characters long.")
    private String surname;

    @Column(name = "NICKNAME", unique = true, nullable = false, length = MAX_LENGTH_OF_FIELD_IN_DB)
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Nickname must be alpha numeric with no spaces")
    @Size(min = MIN_LENGTH_OF_NICKNAME, max = MAX_LENGTH_OF_NICKNAME,
          message = "Nickname must be between 3 and 30 characters long.")
    private String nickname;

    @Column(name = "OPEN_ID_IDENTITY", unique = true, nullable = true)
    private String openIdIdentity;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Size(min = MIN_LENGTH_OF_PASSWORD, message = "Password must be between 6 and 30 characters long.")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
            message = "Invalid email address.")
    @Column(name = "MAIL", nullable = false, length = MAX_LENGTH_OF_EMAIL_IN_DB)
    private String mail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<EnterpriseRatio> enterpriseRatios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<CommentRatio> commentRatios;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Comment> comment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<ProposedEnterprise> proposedEnterprises;


    /*Getters and Setters*/
    public void setProposedEnterprises(Set<ProposedEnterprise> proposedEnterprises) {
        this.proposedEnterprises = proposedEnterprises;
    }

    public Set<ProposedEnterprise> getProposedEnterprises() {
        return proposedEnterprises;
    }

    public void setEnterpriseRatios(Set<EnterpriseRatio> enterpriseRatios) {
        this.enterpriseRatios = enterpriseRatios;
    }

    public Set<EnterpriseRatio> getEnterpriseRatios() {
        return enterpriseRatios;
    }

    public User() {
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setCommentRatios(Set<CommentRatio> commentRatios) {
        this.commentRatios = commentRatios;
    }

    public Set<CommentRatio> getCommentRatios() {
        return commentRatios;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(this.role);
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEnabled() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof User) {
            final User other = (User) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else {
            return false;
        }
    }

    public String getOpenIdIdentity() {
        return openIdIdentity;
    }

    public void setOpenIdIdentity(String openIdIdentity) {
        this.openIdIdentity = openIdIdentity;
    }
}
