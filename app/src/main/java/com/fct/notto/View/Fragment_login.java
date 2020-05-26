package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fct.notto.R;
import com.fct.notto.RoomDatabase.User;
import com.fct.notto.Utils;
import com.fct.notto.ViewModel.UserViewModel;


public class Fragment_login extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, btnRegister;
    private View view;

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
        view = inflater.inflate(R.layout.login_fragment, container, false);

        editTextEmail = view.findViewById(R.id.et_email);
        editTextPassword = view.findViewById(R.id.et_password);
        buttonLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.btn_register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboardFrom(getActivity(), editTextEmail);
                Utils.hideKeyboardFrom(getActivity(), editTextPassword);
                Navigation.findNavController(view).navigate(R.id.action_fragment_login_to_fragment_register);
            }
        });

        return view;
    }

    private void login() {

        String toastLogin = getActivity().getResources().getString(R.string.toast_login_fail);
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        User user = userViewModel.getUserMailAndPass(email, password);

        if (user != null) {
            spLogin();
            Utils.hideKeyboardFrom(getActivity(), view);
            Navigation.findNavController(view).navigate(R.id.loginAction);
        } else {
            Toast.makeText(getActivity(), toastLogin, Toast.LENGTH_SHORT).show();
        }

    }

    private void spLogin() {
        SharedPreferences prefsLogged = getActivity().getSharedPreferences("logged", Context.MODE_PRIVATE);
        SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);

        SharedPreferences.Editor editorLogged = prefsLogged.edit();
        SharedPreferences.Editor editorUserMail = prefsUserMail.edit();

        editorLogged.putBoolean("isLogged", true).apply();
        editorUserMail.putString("Mail", editTextEmail.getText().toString()).apply();
    }

}
