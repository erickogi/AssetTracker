package com.assettrack.assettrack.Models

class UserModel {


    private var id: Int = 0
    private var firstname: String? = null
    private var lastname: String? = null
    private var employeeid: String? = null
    private var role: Int = 0
    private var email: String? = null
    private var designation: String? = null
    private var created_at: String? = null
    private var updated_at: String? = null
    private var full_name: String? = null
    private var rolename: String? = null
    private var token: String? = null

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getFirstname(): String? {
        return firstname
    }

    fun setFirstname(firstname: String) {
        this.firstname = firstname
    }

    fun getLastname(): String? {
        return lastname
    }

    fun setLastname(lastname: String) {
        this.lastname = lastname
    }

    fun getEmployeeid(): String? {
        return employeeid
    }

    fun setEmployeeid(employeeid: String) {
        this.employeeid = employeeid
    }

    fun getRole(): Int {
        return role
    }

    fun setRole(role: Int) {
        this.role = role
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getDesignation(): String? {
        return designation
    }

    fun setDesignation(designation: String) {
        this.designation = designation
    }

    fun getCreated_at(): String? {
        return created_at
    }

    fun setCreated_at(created_at: String) {
        this.created_at = created_at
    }

    fun getUpdated_at(): String? {
        return updated_at
    }

    fun setUpdated_at(updated_at: String) {
        this.updated_at = updated_at
    }

    fun getFull_name(): String? {
        return full_name
    }

    fun setFull_name(full_name: String) {
        this.full_name = full_name
    }

    fun getRolename(): String? {
        return rolename
    }

    fun setRolename(rolename: String) {
        this.rolename = rolename
    }
}