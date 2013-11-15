package com.springapp.mvc.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "enterprise")
public class Enterprise {
    public static final int MAX_LENGTH_OF_NAME = 30;
    public static final int MIN_LENGTH_OF_NAME = 2;
    public static final int MAX_LENGTH_OF_DESCRIPTION_IN_DB = 800;
    public static final int MIN_LENGTH_OF_DESCRIPTION = 5;
    public static final int MIN_LENGTH_OF_WEBSITE_ADDRESS = 4;
    public static final int MIN_LENGTH_OF_PHONE_NUMBER = 5;
    public static final int MAX_LENGTH_OF_PHONE_NUMBER_IN_DB = 20;
    public static final int MIN_LENGTH_OF_ADDRESS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "NAME", nullable = false)
    @Size(min = MIN_LENGTH_OF_NAME, max = MAX_LENGTH_OF_NAME)
    private String name;

    @Column(name = "SUMMARY_RATIO", nullable = false)
    @NotNull
    private Double summaryRatio;

    @Column(name = "DISCOUNT_MIN", nullable = false)
    @NotNull
    private Integer discountMin;

    @Column(name = "DISCOUNT_MAX", nullable = false)
    @NotNull
    private Integer discountMax;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false, length = MAX_LENGTH_OF_DESCRIPTION_IN_DB)
    @Size(min = MIN_LENGTH_OF_DESCRIPTION)
    private String description;

    @Column(name = "MAIL", nullable = false)
    @Email
    private String mail;

    @Column(name = "WEBSITE", nullable = false)
    @Size(min = MIN_LENGTH_OF_WEBSITE_ADDRESS)
    private String webSite;

    @Column(name = "LATITUDE", nullable = false)
    @NotNull
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    @NotNull
    private Double longitude;

    @Column(name = "PHONE", nullable = false, length = MAX_LENGTH_OF_PHONE_NUMBER_IN_DB)
    @Size(min = MIN_LENGTH_OF_PHONE_NUMBER)
    private String phone;

    @Column(name = "ADDRESS", nullable = false)
    @Size(min = MIN_LENGTH_OF_ADDRESS)
    private String address;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EnterpriseRatio> enterpriseRatios;

    @Column(name = "LOGO_NAME", nullable = true)
    private String logoName;

    @Lob
    @Column(name = "LOGO", nullable = true)
    private byte[] logo;

    /*Getters and Setters*/
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setEnterpriseRatios(Set<EnterpriseRatio> enterpriseRatios) {
        this.enterpriseRatios = enterpriseRatios;
    }

    public Set<EnterpriseRatio> getEnterpriseRatios() {
        return enterpriseRatios;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public City getCity() {
        return city;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
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

    public Integer getDiscountMin() {
        return discountMin;
    }

    public void setDiscountMin(Integer discountMin) {
        this.discountMin = discountMin;
    }

    public Integer getDiscountMax() {
        return discountMax;
    }

    public void setDiscountMax(Integer discountMax) {
        this.discountMax = discountMax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Double getSummaryRatio() {
        return summaryRatio;
    }

    public void setSummaryRatio(Double summaryRatio) {
        this.summaryRatio = summaryRatio;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof Enterprise){
            final Enterprise other = (Enterprise) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else{
            return false;
        }
    }

}


