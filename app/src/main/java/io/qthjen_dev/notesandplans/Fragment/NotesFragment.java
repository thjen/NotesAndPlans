package io.qthjen_dev.notesandplans.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import io.qthjen_dev.notesandplans.Activity.AddNote;
import io.qthjen_dev.notesandplans.R;

public class NotesFragment extends Fragment {

    private View mRootView;
    private LinearLayout mBtAddNote;
    private ImageView mIvSearch, mSearchClear;
    private AutoCompleteTextView mSearch;
    private RecyclerView mRecyclerNote;

    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_notes, container, false);

        mBtAddNote = mRootView.findViewById(R.id.bt_createNote);
        mIvSearch = mRootView.findViewById(R.id.iv_search);
        mSearchClear = mRootView.findViewById(R.id.iv_searchClear);
        mSearch = mRootView.findViewById(R.id.et_search);
        mRecyclerNote = mRootView.findViewById(R.id.recyclerViewNote);

        ViewEventClick();

        mBtAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddNote.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_bottom_to_top, R.anim.activity_stay);
            }
        });

        return mRootView;
    }

    private void ViewEventClick() {

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSearch.setCursorVisible(true);

            }
        });

    }



}
