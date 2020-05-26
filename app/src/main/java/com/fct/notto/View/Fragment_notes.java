package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fct.notto.R;
import com.fct.notto.RoomDatabase.Note;
import com.fct.notto.RoomDatabase.NoteDao;
import com.fct.notto.Utils;
import com.fct.notto.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Fragment_notes extends Fragment {

    private EditText editTextTitle, editTextContent;
    private FloatingActionButton fabButton;
    private View view;
    private NoteViewModel noteViewModel;
    private String toastInsert;
    private String toastEdited;
    private String toastSaved;
    private SharedPreferences prefsEdit;
    private SharedPreferences prefsNoteInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notes_fragment, container, false);

        prefsEdit = getActivity().getSharedPreferences("edit", Context.MODE_PRIVATE);
        prefsNoteInfo = getActivity().getSharedPreferences("noteinfo", Context.MODE_PRIVATE);
        boolean isEdit = prefsEdit.getBoolean("editMode", false);

        String titleToEdit = prefsNoteInfo.getString("title", null);
        String contentToEdit = prefsNoteInfo.getString("content", null);

        editTextTitle = view.findViewById(R.id.edit_text_title);
        editTextContent = view.findViewById(R.id.edit_text_content);
        fabButton = view.findViewById(R.id.fabButton);

        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);

        if (isEdit) {
            editTextTitle.setText(titleToEdit);
            editTextContent.setText(contentToEdit);
            fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editNote();
                }
            });
        } else {

            fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNote();
                }
            });
        }

        return view;
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);
        String spUserMail = prefsUserMail.getString("Mail", null);
        toastInsert = getActivity().getResources().getString(R.string.toast_insertTitleContent);
        toastSaved = getActivity().getResources().getString(R.string.toast_saved_note);

        Note newNote = new Note();

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(getActivity(), toastInsert, Toast.LENGTH_SHORT).show();
            return;
        }

        newNote.setTitle(title);
        newNote.setText(content);
        newNote.setUserEmail(spUserMail);
        noteViewModel.insert(newNote);
        Utils.hideKeyboardFrom(getActivity(), view);
        Navigation.findNavController(view).navigate(R.id.action_fragment_notes_to_fragment_board);
        Toast.makeText(getActivity(), toastSaved, Toast.LENGTH_SHORT).show();

    }

    private void editNote() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        String userEmail = prefsNoteInfo.getString("userMail", null);
        Integer id = prefsNoteInfo.getInt("id", 0);
        toastEdited = getActivity().getResources().getString(R.string.toast_edited_note);

        Note editNote = noteViewModel.getNoteById(id);

        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            Toast.makeText(getActivity(), toastInsert, Toast.LENGTH_SHORT).show();
            return;
        }

        editNote.setId(id);
        editNote.setTitle(title);
        editNote.setText(content);
        editNote.setUserEmail(userEmail);
        noteViewModel.update(editNote);
        Utils.hideKeyboardFrom(getActivity(), view);
        Navigation.findNavController(view).navigate(R.id.action_fragment_notes_to_fragment_board);
        Toast.makeText(getActivity(), toastEdited, Toast.LENGTH_SHORT).show();

    }

}
