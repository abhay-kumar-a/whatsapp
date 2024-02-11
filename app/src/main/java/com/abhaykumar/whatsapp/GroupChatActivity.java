package com.abhaykumar.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.abhaykumar.whatsapp.adapters.ChatAdapter;
import com.abhaykumar.whatsapp.databinding.ActivityChatDetailBinding;
import com.abhaykumar.whatsapp.databinding.ActivityGroupChatBinding;
import com.abhaykumar.whatsapp.module.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
 ActivityGroupChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backArrowChatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        // here we use ChatDetailsActivity , message model and adapter
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        // sender id
        final String senderID = FirebaseAuth.getInstance().getUid();
        binding.userNameChatDetail.setText("Friends Group");

        ///set adapter
        final ChatAdapter  adapter = new ChatAdapter(messageModels , this);
        binding.recyclerViewChatDetail.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewChatDetail.setLayoutManager(layoutManager);


        /// get data from firebase database and show on recycler view
        database.getReference().child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();//  first clear all message

                        // using for loop to take all message one by one
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        // onclick Send Message on group chat
        binding.messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = binding.chattextdetail.getText().toString();
                final MessageModel messageModel =new MessageModel(senderID,message);
                // time stamp
                messageModel.setTimestamp(new Date().getTime());
                binding.chattextdetail.setText("");
                database.getReference().child("Group Chat")  // node created in firebase
                           .push()  // get unique id
                        .setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {   // set all values
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }
}