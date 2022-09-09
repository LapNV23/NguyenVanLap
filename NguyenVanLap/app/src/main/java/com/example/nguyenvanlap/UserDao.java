package com.example.nguyenvanlap;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);
    @Update
    int updateUser(User user);
    @Delete
    int deleteUser(User user);
    @Query("SELECT * FROM user where id = :id")
    User findUser(int id);
    @Query("SELECT * FROM user")
    List<User> getAllUser();
}
