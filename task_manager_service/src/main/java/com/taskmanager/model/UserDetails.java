package com.taskmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Column(name = "emp_id")
    String empId;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "user_status")
    String userStatus;
    @Column(name = "id")
    String id;

    public UserDetails() {

    }

    public UserDetails(String id, String empId, String firstName, String lastName, String userStatus) {
        super();
        this.id = id;
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userStatus = userStatus;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getEmpId() {
        return empId;
    }


    public void setEmpId(String empId) {
        this.empId = empId;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }


}
