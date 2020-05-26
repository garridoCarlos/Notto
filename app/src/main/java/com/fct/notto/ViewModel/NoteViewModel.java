package com.fct.notto.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fct.notto.Repository.NottoRepository;
import com.fct.notto.RoomDatabase.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NottoRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NottoRepository(application);
        allNotes = repository.getAllNotes();

    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>>getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>>getAllNotesMail(String mail) {
        return repository.getAllNotesMail(mail);
    }

    public Note getNoteById(int id) {
        return repository.getNoteById(id);
    }
}
