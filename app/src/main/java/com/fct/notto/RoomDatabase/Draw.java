package com.fct.notto.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "draw_table",
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "email",
        childColumns = "userEmail",
        onDelete = ForeignKey.CASCADE))
public class Draw {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String title;
    private String bitcode;
    @NonNull
    private String userEmail;

    public Draw(String title, String bitcode, @NonNull String userEmail) {
        this.title = title;
        this.bitcode = bitcode;
        this.userEmail = userEmail;
    }

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

    public String getBitcode() {
        return bitcode;
    }

    public void setBitcode(String bitcode) {
        this.bitcode = bitcode;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }
}
