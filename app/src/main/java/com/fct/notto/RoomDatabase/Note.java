package com.fct.notto.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table",
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "email",
        childColumns = "userEmail",
        onDelete = ForeignKey.CASCADE))
public class Note {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String title;
    private String text;
    @NonNull
    private String userEmail;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }
}
