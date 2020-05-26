package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.fct.notto.R;
import com.fct.notto.Utils;
import com.fct.notto.ViewModel.UserViewModel;

public class Fragment_user extends Fragment {

    View view;
    private TextView tvuserMail;
    private TextView tvuserName;
    private Button btChangeName, btChangeMail, btChangePass;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);

        tvuserMail = view.findViewById(R.id.tvuserEmail);
        tvuserName = view.findViewById(R.id.tvuserName);
        btChangeName = view.findViewById(R.id.btChangeName);
        btChangeMail= view.findViewById(R.id.btChangeMail);
        btChangePass= view.findViewById(R.id.btChangePassword);

        SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);
        String spUserMail = prefsUserMail.getString("Mail", null);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        String username = userViewModel.getUserNameAccount(spUserMail);
        Character userInitial = username.charAt(0);

        tvuserMail.setText(spUserMail);
        tvuserName.setText(username);

        ImageView imgProfile = view.findViewById(R.id.imgUserProfile);
        TextDrawable imgHeader = TextDrawable.builder().beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(String.valueOf(userInitial), Color.rgb(27, 94, 32), 10);
        imgProfile.setImageDrawable(imgHeader);

        btChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_user_to_fragment_editUserName);
            }
        });

        btChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_user_to_fragment_editUserPass);
            }
        });

        return view;
    }
}
