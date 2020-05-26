package com.fct.notto.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DrawDao {

    @Insert
    void insert(Draw draw);

    @Update
    void update(Draw draw);

    @Delete
    void delete(Draw draw);

    @Query("DELETE FROM draw_table")
    void deleteAllDrawings();

    @Query("SELECT * FROM draw_table ORDER BY id")
    LiveData<List<Draw>> getAllDrawings();



}
