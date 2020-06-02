package com.fct.notto.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fct.notto.Adapter.NoteAdapter;
import com.fct.notto.R;
import com.fct.notto.RoomDatabase.Note;
import com.fct.notto.ViewModel.NoteViewModel;
import java.util.Collections;
import java.util.List;

public class Fragment_board extends Fragment {

    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board_fragment, container, false);

        final String toastDelete = getActivity().getResources().getString(R.string.toast_note_deleted);
        final String toastEdit = getActivity().getResources().getString(R.string.toast_edit_note);
        SharedPreferences prefsUserMail = getActivity().getSharedPreferences("userMail", Context.MODE_PRIVATE);
        final String spUserMail = prefsUserMail.getString("Mail", null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        final NoteViewModel noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);
            noteViewModel.getAllNotesMail(spUserMail).observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    adapter.setNotes(notes);
                }
            });


            //Gestiona el borrado y cambio de posición de las notas deslizándolas.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN| ItemTouchHelper.UP,
                ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();

                Collections.swap(adapter.getNotes(), position_dragged, position_target);
                adapter.notifyItemMoved(position_dragged, position_target);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), toastDelete , Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                SharedPreferences prefsEdit = getActivity().getSharedPreferences("edit", Context.MODE_PRIVATE);
                SharedPreferences prefsNoteInfo = getActivity().getSharedPreferences("noteInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editorEdit = prefsEdit.edit();
                SharedPreferences.Editor editorNoteinfo = prefsNoteInfo.edit();

                editorEdit.putBoolean("editMode", true).apply();
                editorNoteinfo.putInt("id", note.getId()).apply();
                editorNoteinfo.putString("title", note.getTitle()).apply();
                editorNoteinfo.putString("content", note.getText()).apply();
                editorNoteinfo.putString("userMail", note.getUserEmail()).apply();

                Navigation.findNavController(view).navigate(R.id.action_fragment_board_to_fragment_notes);
                Toast.makeText(getActivity(), toastEdit , Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
