package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fct.notto.R;
import com.fct.notto.RoomDatabase.User;
import com.fct.notto.Utils;
import com.fct.notto.ViewModel.UserViewModel;

public class Fragment_editUserPass extends Fragment {

    View view;
    private TextView tvuserMail;
    private TextView tvuserName;
    private EditText etOldPass, etNewPass, etNewRePass;
    private Button btChangePass;
    private String passNotMatch, errorPass, errorValidPass, wrongPass, passwordChanged;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_userpassword_fragment, container, false);

        tvuserMail = view.findViewById(R.id.tvuserEmail);
        tvuserName = view.findViewById(R.id.tvuserName);
        etOldPass = view.findViewById(R.id.etOldPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        etNewRePass = view.findViewById(R.id.etNewRepass);
        btChangePass = view.findViewById(R.id.btChangePass);

        final SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);
        final String spUserMail = prefsUserMail.getString("Mail", null);
        final UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        String userName = userViewModel.getUserNameAccount(spUserMail);
        Character userInitial = userName.charAt(0);

        tvuserMail.setText(spUserMail);
        tvuserName.setText(userName);

        ImageView imgProfile = view.findViewById(R.id.imgUserProfile);
        TextDrawable imgHeader = TextDrawable.builder().beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(String.valueOf(userInitial), Color.rgb(27, 94, 32), 10);
        imgProfile.setImageDrawable(imgHeader);

        btChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = etOldPass.getText().toString().trim();
                String newPass = etNewPass.getText().toString().trim();
                String newRepass = etNewRePass.getText().toString().trim();
                passNotMatch = getActivity().getResources().getString(R.string.toast_password_notmatch);
                errorPass = getActivity().getResources().getString(R.string.error_putPassword);
                errorValidPass = getActivity().getResources().getString(R.string.toast_validPassword);
                wrongPass = getActivity().getResources().getString(R.string.wrongPass);
                passwordChanged = getActivity().getResources().getString(R.string.passwordChanged);


                User user = userViewModel.getUserMail(spUserMail);

                if (!user.getPassword().equals(oldPass)) {
                    etOldPass.setError(wrongPass);
                } else if (newPass.isEmpty()) {
                    etNewPass.setError(errorPass);
                } else if (!Utils.PASSWORD_PATTERN.matcher(newPass).matches()) {
                    etNewRePass.setError(errorValidPass);
                } else if (!newPass.equals(newRepass)) {
                    etNewRePass.setError(passNotMatch);
                } else {

                    user.setPassword(newPass);
                    userViewModel.update(user);
                    Utils.hideKeyboardFrom(getActivity(), view);
                    Navigation.findNavController(view).navigate(R.id.action_fragment_editUserPass_to_fragment_user);
                    Toast.makeText(getActivity(), passwordChanged, Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }
}
