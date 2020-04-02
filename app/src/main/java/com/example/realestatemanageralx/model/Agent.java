package com.example.realestatemanageralx.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Agent")
public class Agent {

    /**
     * The unique identifier of the real estate agent
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "aid")
    private long id;

    /**
     * The first name of the real estate agent
     */
    @ColumnInfo(name="firstName")
    @NonNull
    private String firstName;

    /**
     * The last name of the real estate agent
     */
    @ColumnInfo(name="lastName")
    @NonNull
    private String lastName;

    /**
     * The username of the real estate agent
     */
    @ColumnInfo(name="userName")
    @NonNull
    private String userName;

    /**
     * The password of the real estate agent
     */
    @ColumnInfo(name="password")
    @NonNull
    private String password;

    /**
     * The email address of the real estate agent
     */
    @ColumnInfo(name="email")
    @NonNull
    private String email;

    /**
     * The phone number of the real estate agent
     */
    @ColumnInfo(name="phone")
    @NonNull
    private String phone;

    public Agent( String firstName,  String lastName, String userName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }


    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public long getId() {
        return id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

}
