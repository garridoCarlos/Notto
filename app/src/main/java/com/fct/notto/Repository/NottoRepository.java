package com.fct.notto.Repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.fct.notto.RoomDatabase.Draw;
import com.fct.notto.RoomDatabase.DrawDao;
import com.fct.notto.RoomDatabase.Note;
import com.fct.notto.RoomDatabase.NoteDao;
import com.fct.notto.RoomDatabase.NottoDatabase;
import com.fct.notto.RoomDatabase.User;
import com.fct.notto.RoomDatabase.UserDao;
import java.util.List;


public class NottoRepository {

    private UserDao userDao;
    private NoteDao noteDao;
    private DrawDao drawDao;
    private UserDao userDaoMainThread;
    private NoteDao noteDaoMainThread;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Draw>> allDrawings;
    private LiveData<List<User>> allUsers;

    public NottoRepository(Application application) {

        NottoDatabase database = NottoDatabase.getInstance(application);
        userDao = database.userDao();
        noteDao = database.noteDao();
        drawDao = database.drawDao();
        userDaoMainThread = Room.databaseBuilder(application,
                NottoDatabase.class, "notto_database")
                .allowMainThreadQueries()
                .build().userDao();
        noteDaoMainThread = Room.databaseBuilder(application,
                NottoDatabase.class, "notto_database")
                .allowMainThreadQueries()
                .build().noteDao();

        allNotes = noteDao.getAllNotes();
        allDrawings = drawDao.getAllDrawings();
        allUsers = userDao.getUsers();

    }

    //-------------------------------Note methods-------------------------
    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getAllNotesMail(String mail) {
        return noteDao.getAllNotesMail(mail);
    }

    public Note getNoteById(int id) {
        return noteDaoMainThread.getNoteById(id);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

    //--------------------------------Draw methods----------------------------------

    public void insert(Draw draw) {
        new InsertDrawAsyncTask(drawDao).execute(draw);
        ;
    }

    public void update(Draw draw) {
        new UpdateDrawAsyncTask(drawDao).execute(draw);
        ;
    }

    public void delete(Draw draw) {
        new DeleteDrawAsyncTask(drawDao).execute(draw);
        ;
    }

    public void deleteAllDrawings() {
        new DeleteAllDrawAsyncTask(drawDao).execute();
    }

    public LiveData<List<Draw>> getAllDrawings() {
        return allDrawings;
    }

    private static class InsertDrawAsyncTask extends AsyncTask<Draw, Void, Void> {
        private DrawDao drawDao;

        private InsertDrawAsyncTask(DrawDao drawDao) {
            this.drawDao = drawDao;
        }

        @Override
        protected Void doInBackground(Draw... drawings) {
            drawDao.insert(drawings[0]);
            return null;
        }
    }

    private static class UpdateDrawAsyncTask extends AsyncTask<Draw, Void, Void> {
        private DrawDao drawDao;

        private UpdateDrawAsyncTask(DrawDao drawDao) {
            this.drawDao = drawDao;
        }

        @Override
        protected Void doInBackground(Draw... drawings) {
            drawDao.update(drawings[0]);
            return null;
        }
    }

    private static class DeleteDrawAsyncTask extends AsyncTask<Draw, Void, Void> {
        private DrawDao drawDao;

        private DeleteDrawAsyncTask(DrawDao drawDao) {
            this.drawDao = drawDao;
        }

        @Override
        protected Void doInBackground(Draw... drawings) {
            drawDao.delete(drawings[0]);
            return null;
        }
    }

    private static class DeleteAllDrawAsyncTask extends AsyncTask<Void, Void, Void> {
        private DrawDao drawDao;

        private DeleteAllDrawAsyncTask(DrawDao drawDao) {
            this.drawDao = drawDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            drawDao.deleteAllDrawings();
            return null;
        }
    }

    //---------------------------------User methods------------------------------------

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getUser() {
        return allUsers;
    }

    public User getUserMailAndPass(String mail, String pass) {
        return userDaoMainThread.getUserMailAndPass(mail, pass);
    }

    public User getUserMail(String mail) {
        return userDaoMainThread.getUserMail(mail);
    }

    public String getUserNameAccount(String mail){
        return userDaoMainThread.getUserNameAccount(mail);
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class GetUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.getUsers();
            return null;
        }
    }

}
