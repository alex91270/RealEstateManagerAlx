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
    @ColumnInfo(name = "aId")
    private long aId;


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
}
