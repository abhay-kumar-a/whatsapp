package com.abhaykumar.whatsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhaykumar.whatsapp.ChatDetailActivity;
import com.abhaykumar.whatsapp.R;
import com.abhaykumar.whatsapp.module.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<Users> list;
    Context context;
    public UserAdapter(ArrayList<Users> list,Context context)
    {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // use of view and use layout inflater

        View view = LayoutInflater.from(context).inflate(R.layout.samplerecyclerlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {   // insert data
        Users users = list.get(position);
        // use of picasso for take image online
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.profilepic).into(holder.imageView);
        //userName
        holder.username.setText(users.getUserName()); // lastMassage


        // show last message in sender and receiver for (message will be taken from firebase)
        // take data from firebase
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+users.getUserId())   // go in sender in main firebase
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            for (DataSnapshot snapshot1 : snapshot.getChildren())
                            {
                                holder.lastMassage.setText(snapshot1.child("message").getValue(String.class).toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        //Send data to chat detail activity using intent and putExtra (send  data from one activity to another use  putExtra )
        holder.itemView.setOnClickListener(new View.OnClickListener() {   // itemView
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(context, ChatDetailActivity.class);
                //use of putExtra
                intent.putExtra("userId",users.getUserId());  // show one error ,so go in model and delete setter and getter of UserId and create again
                intent.putExtra("profilePic",users.getProfilePic());
                intent.putExtra("userName",users.getUserName());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView username,lastMassage;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.userName);
            lastMassage = itemView.findViewById(R.id.lastMassage);
        }
    }
}
