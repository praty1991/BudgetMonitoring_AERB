package com.aerb.budget.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.aerb.budget.entity.MyEnums.Status;
import com.aerb.budget.entity.MyEnums.Position;
import com.aerb.budget.entity.MyEnums.Role;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {@Id
    @Column(name="emp_id")
    private String empId;
    
    @Column(unique = true, nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String designation;
    
    @Column(nullable = false)
    private String division;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position; // EMPLOYEE, SECTION_HEAD, DIVISION_HEAD, CAO
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

	@Column(unique = true, name="uid")
    private String uId;

	@Column
    private String password;
    
    private String section;
    
    private String intercom;
    
    private String mobile;
    
    private String digitalSignaturePath;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    


	public String getEmpId() {
		return empId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getUid() {
		return uId;
	}

	public void setUid(String uid) {
		this.uId = uid;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getIntercom() {
		return intercom;
	}

	public void setIntercom(String intercom) {
		this.intercom = intercom;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDigitalSignaturePath() {
		return digitalSignaturePath;
	}

	public void setDigitalSignaturePath(String digitalSignaturePath) {
		this.digitalSignaturePath = digitalSignaturePath;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    }
