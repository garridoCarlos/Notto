package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

public class Fragment_editUserName extends Fragment {

    View view;
    private TextView tvuserMail;
    private TextView tvuserName;
    private EditText etNewName;
    private Button btNewName;
    private String errorName;
    private String username;
    private Character userinitial;
    private String usernameChanged;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_username_fragment, container, false);

        tvuserMail = view.findViewById(R.id.tvuserEmail);
        tvuserName = view.findViewById(R.id.tvuserName);
        etNewName = view.findViewById(R.id.et_newName);
        btNewName = view.findViewById(R.id.btUserName);

        usernameChanged = getActivity().getResources().getString(R.string.usernameChanged);

        SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);
        final String spUserMail = prefsUserMail.getString("Mail", null);
        final UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        username = userViewModel.getUserNameAccount(spUserMail);
        userinitial = username.charAt(0);

        tvuserMail.setText(spUserMail);
        tvuserName.setText(username);

        ImageView imgProfile = view.findViewById(R.id.imgUserProfile);
        TextDrawable imgHeader = TextDrawable.builder().beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(String.valueOf(userinitial), Color.rgb(27, 94, 32), 10);
        imgProfile.setImageDrawable(imgHeader);

        btNewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewUserName = etNewName.getText().toString().trim();
                errorName = getActivity().getResources().getString(R.string.error_putName);

                User user = userViewModel.getUserMail(spUserMail);

                if (NewUserName.isEmpty()) {
                    etNewName.setError(errorName);
                    return;
                }
                user.setUserName(NewUserName);
                userViewModel.update(user);
                Utils.hideKeyboardFrom(getActivity(), view);
                Navigation.findNavController(view).navigate(R.id.action_fragment_editUserName_to_fragment_user);
                Toast.makeText(getActivity(), usernameChanged, Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
