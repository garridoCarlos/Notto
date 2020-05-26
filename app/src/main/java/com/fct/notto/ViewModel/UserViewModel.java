package com.fct.notto.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fct.notto.Repository.NottoRepository;
import com.fct.notto.RoomDatabase.Draw;
import com.fct.notto.RoomDatabase.User;
import com.fct.notto.RoomDatabase.UserDao;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private NottoRepository repository;
    private LiveData<List<User>> getUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new NottoRepository(application);
        getUser = repository.getUser();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public LiveData<List<User>>getGetUser() {
        return getUser;
    }

    public User getUserMailAndPass(String email, String password){
        return repository.getUserMailAndPass(email, password);
    }

    public User getUserMail(String email){
        return repository.getUserMail(email);
    }

    public String getUserNameAccount(String mail){
        return repository.getUserNameAccount(mail);
    }
}
