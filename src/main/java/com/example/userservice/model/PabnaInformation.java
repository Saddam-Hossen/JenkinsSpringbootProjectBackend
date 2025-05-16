package com.example.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PabnaInformation")
public class PabnaInformation {

    @Id
    private String id;

    private String upazila;
    private String upazilaIdNumber;

    private String union;
    private String unionIdNumber;

    private String voting_center;
    private String votingCenterIdNumber;

    private String village;
    private String name;
    private String father_name;
    private String categoryName;

    private String organizational_responsibility;
    private String organizational_level;

    private String mobile_number;
    private String comments;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpazila() {
        return upazila;
    }

    public void setUpazila(String upazila) {
        this.upazila = upazila;
    }

    public String getUpazilaIdNumber() {
        return upazilaIdNumber;
    }

    public void setUpazilaIdNumber(String upazilaIdNumber) {
        this.upazilaIdNumber = upazilaIdNumber;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getUnionIdNumber() {
        return unionIdNumber;
    }

    public void setUnionIdNumber(String unionIdNumber) {
        this.unionIdNumber = unionIdNumber;
    }

    public String getVoting_center() {
        return voting_center;
    }

    public void setVoting_center(String voting_center) {
        this.voting_center = voting_center;
    }

    public String getVotingCenterIdNumber() {
        return votingCenterIdNumber;
    }

    public void setVotingCenterIdNumber(String votingCenterIdNumber) {
        this.votingCenterIdNumber = votingCenterIdNumber;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrganizational_responsibility() {
        return organizational_responsibility;
    }

    public void setOrganizational_responsibility(String organizational_responsibility) {
        this.organizational_responsibility = organizational_responsibility;
    }

    public String getOrganizational_level() {
        return organizational_level;
    }

    public void setOrganizational_level(String organizational_level) {
        this.organizational_level = organizational_level;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
