package com.fct.notto.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user_table where email= :mail and password= :password")
    User getUserMailAndPass(String mail, String password);

    @Query("SELECT * FROM user_table where email= :mail")
    User getUserMail(String mail);

    @Query("SELECT userName FROM user_table where email= :mail")
    String getUserNameAccount(String mail);


}
