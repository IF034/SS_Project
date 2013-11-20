package com.springapp.mvc.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import javax.validation.constraints.Size;
import java.util.Set;

/** Describes table 'City' in database. **/
@Entity
@Table(name = "city")
public class City {
    /**Maximum field size in database. **/
    public static final int MAX_LENGTH_OF_FIELD_IN_DB = 30;
    /**Minimum 'name' field size for city in database. **/
    public static final int MIN_LENGTH_OF_NAME = 2;
    /**Maximum 'name' field size for city in database. **/
    public static final int MAX_LENGTH_OF_NAME = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = MAX_LENGTH_OF_FIELD_IN_DB)
    @Size(min = MIN_LENGTH_OF_NAME, max = MAX_LENGTH_OF_NAME)
    private String name;

    @OneToMany(mappedBy = "city",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Enterprise> enterprises;

    /*Getters and Setters*/
    public void setEnterprises(Set<Enterprise> enterprises) {
        this.enterprises = enterprises;
    }

    public Set<Enterprise> getEnterprises() {
        return enterprises;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof City) {
            final City other = (City) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else {
            return false;
        }
    }
}

