package com.fct.notto.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.fct.notto.R;
import com.fct.notto.RoomDatabase.NottoDatabase;
import com.fct.notto.RoomDatabase.User;
import com.fct.notto.RoomDatabase.UserDao;
import com.fct.notto.Utils;
import com.fct.notto.ViewModel.UserViewModel;

import java.util.List;

public class Fragment_register extends Fragment {
    private EditText etNombre, etMail, etPassword, etRepassword;
    private Button btRegister, btBackToLogin;
    private View view;
    private String toastEmail, toastPassword, errorName, errorMail, errorPass, errorValidMail, errorValidPass;
    private UserViewModel userViewModel;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fragment, container, false);

        etNombre = view.findViewById(R.id.et_name);
        etMail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etRepassword = view.findViewById(R.id.et_repassword);
        btRegister = view.findViewById(R.id.btn_register);
        btBackToLogin = view.findViewById(R.id.btn_backtologin);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
                insertUser();
            }
        });

        btBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_register_to_fragment_login);
            }
        });

        return view;
    }

    private boolean validateInput() {
        toastEmail = getActivity().getResources().getString(R.string.toast_email_used);
        toastPassword = getActivity().getResources().getString(R.string.toast_password_notmatch);
        errorName = getActivity().getResources().getString(R.string.error_putName);
        errorMail = getActivity().getResources().getString(R.string.error_putMail);
        errorPass = getActivity().getResources().getString(R.string.error_putPassword);
        errorValidMail = getActivity().getResources().getString(R.string.error_validEmail);
        errorValidPass = getActivity().getResources().getString(R.string.toast_validPassword);
        String name = etNombre.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repassword = etRepassword.getText().toString().trim();
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        User user = userViewModel.getUserMail(mail);

        if (user != null) {
            etMail.setError(toastEmail);
            return false;
        } else if (name.isEmpty()) {
            etNombre.setError(errorName);
            return false;
        } else if (mail.isEmpty()) {
            etMail.setError(errorMail);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            etMail.setError(errorValidMail);
            return false;
        } else if (password.isEmpty()) {
            etPassword.setError(errorPass);
            return false;
        } else if (!Utils.PASSWORD_PATTERN.matcher(password).matches()) {
            etMail.setError(errorValidPass);
            return false;
        } else if (!password.equals(repassword)) {
            etRepassword.setError(toastPassword);
            return false;
        } else {
            return true;
        }
    }

    private void insertUser() {
        if(!validateInput()){
            return;
        }
        String name = etNombre.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repassword = etRepassword.getText().toString().trim();

            User newUser = new User();
            newUser.setUserName(name);
            newUser.setEmail(mail);
            newUser.setPassword(password);
            userViewModel.insert(newUser);
            Navigation.findNavController(view).navigate(R.id.action_fragment_register_to_fragment_login);

    }
}
