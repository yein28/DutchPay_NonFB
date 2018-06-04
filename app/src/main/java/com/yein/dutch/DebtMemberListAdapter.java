package com.yein.dutch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DebtMemberListAdapter extends BaseAdapter {

    private ArrayList<Member> members;

    DebtMemberListAdapter(ArrayList<Member> members){
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Member getItem(int i) {
        return members.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        if( view == null ){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.id_ratingbar, viewGroup, false);
        }

        RatingBar ratingBar = view.findViewById(R.id.rb_debt_member_rate);
        TextView id = view.findViewById(R.id.tv_debt_member_id);

        ratingBar.setRating(Float.parseFloat(getItem(i).getRate()));
        id.setText(getItem(i).getId());

        return view;
    }
}
