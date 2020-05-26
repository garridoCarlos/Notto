package com.fct.notto.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fct.notto.Repository.NottoRepository;
import com.fct.notto.RoomDatabase.Draw;
import com.fct.notto.RoomDatabase.Note;

import java.util.List;

public class DrawViewModel extends AndroidViewModel {
    private NottoRepository repository;
    private LiveData<List<Draw>> allDrawings;

    public DrawViewModel(@NonNull Application application) {
        super(application);
        repository = new NottoRepository(application);
        allDrawings = repository.getAllDrawings();
    }

    public void insert(Draw draw) {
        repository.insert(draw);
    }

    public void update(Draw draw) {
        repository.update(draw);
    }

    public void delete(Draw draw) {
        repository.delete(draw);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Draw>>getAllNotes() {
        return allDrawings;
    }
}
