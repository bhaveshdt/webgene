package com.delrima.webgene.client.dto;

// Generated Feb 23, 2008 11:17:51 PM by Hibernate Tools 3.2.0.b11

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Configurable;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
@Configurable
@Entity
@Table(name = "contact")
public class Contact implements Serializable, IsSerializable {

    @Column(name = "CITY", length = 50)
    private String city;

    @Column(name = "COUNTRY", length = 50)
    private String country;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "HOMEPHONE", length = 20)
    private String homephone;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "IM", length = 100)
    private String im;

//    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//    private transient Set<Member> members;

    @Column(name = "MOBILEPHONE", length = 20)
    private String mobilephone;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "STATE", length = 50)
    private String state;

    @Column(name = "STREET1", length = 100)
    private String street1;

    @Column(name = "STREET2", length = 100)
    private String street2;

    @Column(name = "WEBSITE", length = 255)
    private String website;

    @Column(name = "WORKPHONE", length = 20)
    private String workphone;

    @Column(name = "ZIP", length = 20)
    private String zip;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getHomephone() {
        return homephone;
    }

    public Long getId() {
        return this.id;
    }

    public String getIm() {
        return im;
    }

//    public Set<Member> getMembers() {
//        return members;
//    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getWebsite() {
        return website;
    }

    public String getWorkphone() {
        return workphone;
    }

    public String getZip() {
        return zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIm(String im) {
        this.im = im;
    }

//    public void setMembers(Set<Member> members) {
//        this.members = members;
//    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
