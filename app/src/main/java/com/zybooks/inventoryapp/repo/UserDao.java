package com.zybooks.inventoryapp.repo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.zybooks.inventoryapp.model.User;


@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE name = :userName AND password = :userPassword LIMIT 1")
    User getUser(String userName, String userPassword);

    @Query("SELECT * FROM User WHERE name = :userName")
    User checkForName(String userName);


    @Insert
    long createUser(User user);


}
