package com.fct.notto.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Note.class, Draw.class}, version = 1)
public abstract class NottoDatabase extends RoomDatabase {

    private static NottoDatabase instance;

    public abstract UserDao userDao();
    public abstract NoteDao noteDao();
    public abstract DrawDao drawDao();

    public static synchronized NottoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NottoDatabase.class, "notto_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
