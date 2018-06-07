package com.yein.dutch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DebtListWithMoneyAdapter extends BaseAdapter{
    private ArrayList<Member> members;

    DebtListWithMoneyAdapter(ArrayList<Member> members){
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
            view = inflater.inflate(R.layout.id_ratingbar_money, viewGroup, false);
        }

        TextView id = view.findViewById(R.id.tv_complete_debt_id);
        RatingBar ratingBar = view.findViewById(R.id.rb_complete_debt_rate);
        TextView money = view.findViewById(R.id.tv_complete_debt_money);

        id.setText(getItem(i).getId());
        ratingBar.setRating(Float.parseFloat(getItem(i).getRate()));
        money.setText(getItem(i).getMoney());

        return view;
    }
}
