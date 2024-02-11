package com.abhaykumar.whatsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhaykumar.whatsapp.R;
import com.abhaykumar.whatsapp.adapters.UserAdapter;
import com.abhaykumar.whatsapp.databinding.FragmentChatsFragmentsBinding;
import com.abhaykumar.whatsapp.module.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatsFragments extends Fragment {

    public ChatsFragments() {
        // Required empty public constructor
    }
    FragmentChatsFragmentsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsFragmentsBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();//////////////////////////////////////////////

        // setAdapter and Layout Manager;
        UserAdapter adapter = new UserAdapter(list,getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        //lineaLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        // use of firebase for take data from database
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear(); // for clear bugs and show recyclerview on screen of chat
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users  = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}