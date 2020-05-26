package com.fct.notto.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fct.notto.R;

public class Fragment_canvas extends Fragment {
    private View view;
    private String toastWIP;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.canvas_fragment, container, false);

        toastWIP = getActivity().getResources().getString(R.string.toast_WIP);

        Toast.makeText(getActivity(), toastWIP , Toast.LENGTH_SHORT).show();

        return view;
    }
}
