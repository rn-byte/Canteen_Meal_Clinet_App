package com.inim.canteenmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeFragment extends Fragment {
   RecyclerView notice_rv;
    MyAdapterForNoticeDisplay adapter;

    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notice, container, false);

        notice_rv=view.findViewById(R.id.notice_rv);
        notice_rv.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<noticeModelForRead> options=new FirebaseRecyclerOptions.Builder<noticeModelForRead>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("notice"),noticeModelForRead.class ).build();
        adapter=new MyAdapterForNoticeDisplay(options);
        notice_rv.setAdapter(adapter);



        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}