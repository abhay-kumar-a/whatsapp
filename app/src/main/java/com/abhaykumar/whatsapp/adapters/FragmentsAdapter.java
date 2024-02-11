package com.abhaykumar.whatsapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abhaykumar.whatsapp.fragments.CallsFragments;
import com.abhaykumar.whatsapp.fragments.ChatsFragments;
import com.abhaykumar.whatsapp.fragments.StatusFragments;

import java.util.Calendar;

public class FragmentsAdapter extends FragmentPagerAdapter {
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChatsFragments();
            case 1: return  new StatusFragments();
            case 2: return new CallsFragments();
            default: return new ChatsFragments();
        }
    }

    @Override
    public int getCount() {
        return 3; // we have three fragments so.

    }

    // for give page titles use; Method - getPageTitle
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       String title = null;
       if(position==0)
       {
           title ="CHATS";
       }
       if(position==1){
           title = "STATUS";
       }
       if (position==2){
           title="CALLS";
       }
       return title;
    }
}
