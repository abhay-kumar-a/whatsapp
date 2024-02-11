package com.abhaykumar.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;

import com.abhaykumar.whatsapp.adapters.ChatAdapter;
import com.abhaykumar.whatsapp.databinding.ActivityChatDetailBinding;
import com.abhaykumar.whatsapp.module.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // git instance of FirebaseDatabase and FirebaseAuth
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

       //  Receive  User Data in Chat Activity....


        final String senderId = auth.getUid();   // when user is login is that == sender
        String receiveId = getIntent().getStringExtra("userId");  // receiver is get data from sender
        String userName = getIntent().getStringExtra("userName"); // data is received
        String profilePic = getIntent().getStringExtra("profilePic");

        // set data from these view
        binding.userNameChatDetail.setText(userName);
        //use of picasso for take image
        Picasso.get().load(profilePic).placeholder(R.drawable.profilepic).into(binding.profileImage);
        //work on back arrow of chatDetail
        binding.backArrowChatDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this,MainActivity.class); // when click on back arrow it will go in MainActivity
                startActivity(intent);
            }
        });

 // new one
        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels , this);
        binding.recyclerViewChatDetail.setAdapter(chatAdapter);
        // LayoutManager (linear layout)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewChatDetail.setLayoutManager(layoutManager);

        // setOnClickListener On send Button
        final String senderRoom  = senderId+receiveId;
        final String receiverRoom  = receiveId+senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // use loop for multiple message
                        messageModels.clear(); // for clear chat data // nighter it get same data again and again.
                        for(DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        /// for notify instant data
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // work on edit text
                String message = binding.chattextdetail.getText().toString();
                final MessageModel model = new MessageModel(senderId , message);
                // set time stamp
                model.setTimestamp(new Date().getTime());

                // for make empty text view when we send message
                binding.chattextdetail.setText("");

                // work on database // for create node of sender+ receiver in firebase ;
                database.getReference().child("chats")
                       .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });

            }
        });


    }
}