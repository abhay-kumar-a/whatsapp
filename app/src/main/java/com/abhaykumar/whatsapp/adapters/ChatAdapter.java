package com.abhaykumar.whatsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhaykumar.whatsapp.R;
import com.abhaykumar.whatsapp.module.MessageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

// min second process we extend recycler view in main class
public class ChatAdapter  extends  RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE =2;
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    // methods are implement
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender , parent  , false);
            return new SenderViewHolder(view);
        }
        else {
            View view  =LayoutInflater.from(context).inflate(R.layout.sample_receiver , parent , false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override  // Third Process
    public int getItemViewType(int position)
    {
        if(messageModels.get(position).getuTd().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }
    @Override  // fourth process
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     MessageModel messageModel = messageModels.get(position);

     if(holder.getClass() == SenderViewHolder.class)   // its a runtime exception , whole work done with firebase
     {
         ((SenderViewHolder) holder).senderMsg.setText(messageModel.getMessage());  /// sender message are set
     }
     else {
         ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage()); // receiver message are set
     }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    // first process
    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {
       TextView receiverMsg ,  receiverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.receiveText);
            receiverTime = itemView.findViewById(R.id.receiverTime);

        }
    }
    public class SenderViewHolder extends  RecyclerView.ViewHolder
    {
    TextView senderMsg  , senderTime ;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
    // first process end
}
