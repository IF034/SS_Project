package com.springapp.mvc.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ProposedEnterprise {

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

    @Column(name = "MAIL", nullable = true)
    @Email
    private String mail;

    @Column(name = "WEBSITE", nullable = true)
    @Size(min = MIN_LENGTH_OF_WEBSITE_ADDRESS)
    private String webSite;

    @Column(name = "PHONE", nullable = false, length = MAX_LENGTH_OF_PHONE_NUMBER_IN_DB)
    @Size(min = MIN_LENGTH_OF_PHONE_NUMBER)
    private String phone;

    @Column(name = "ADDRESS", nullable = false)
    @Size(min = MIN_LENGTH_OF_ADDRESS)
    private String address;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "LOGO_PATH", nullable = true)
    private String logoPath;

    @Lob
    @Column(name = "LOGO", nullable = true)
    private byte[] logo;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private User user;

    /*Getter and setters*/
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscountMin(Integer discountMin) {
        this.discountMin = discountMin;
    }

    public void setDiscountMax(Integer discountMax) {
        this.discountMax = discountMax;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscountMin() {
        return discountMin;
    }

    public Integer getDiscountMax() {
        return discountMax;
    }

    public String getMail() {
        return mail;
    }

    public String getWebSite() {
        return webSite;
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

    public String getLogoPath() {
        return logoPath;
    }

    public byte[] getLogo() {
        return logo;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof ProposedEnterprise){
            final ProposedEnterprise other = (ProposedEnterprise) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .isEquals();
        } else{
            return false;
        }
    }
}
