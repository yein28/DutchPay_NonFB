package com.yein.dutch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private ArrayList<Member> members;
    String type;


    ExpandableListAdapter(ArrayList<Member> members, String type){
        this.members = members;
        this.type = type;
    }

    @Override
    public int getGroupCount() {
        return members.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Member getGroup(int i) {
        return members.get(i);
    }

    @Override
    public Member getChild(int i, int i1) {
        return members.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        if( view == null ){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.parent_list, viewGroup, false);
        }

        TextView id = view.findViewById(R.id.tv_id);

        id.setText(getGroup(i).getId());


        ImageView arrow = view.findViewById(R.id.arrow);

        if (b) {
            arrow.setImageResource(R.drawable.up_arrow);
        }
        else {
            arrow.setImageResource(R.drawable.down_arrow);
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        Member m = getGroup(i);

        if(type.equalsIgnoreCase("debt")) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.debt_child_list, viewGroup, false);
            }

            RatingBar rate = view.findViewById(R.id.rc_rate);
            TextView money = view.findViewById(R.id.tv_money);
            TextView date = view.findViewById(R.id.tv_date);
            TextView bank = view.findViewById(R.id.tv_bank);
            TextView account = view.findViewById(R.id.tv_account);

            rate.setRating(Float.parseFloat(m.getRate()));
            money.setText(m.getMoney());
            date.setText(m.getDate());
            bank.setText(m.getBank());
            account.setText(m.getAccount());

            return view;
        } else if(type.equalsIgnoreCase("loan")) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.debt_child_list, viewGroup, false);
            }
            RatingBar rate = view.findViewById(R.id.rate);
            TextView money = view.findViewById(R.id.tv_money);
            TextView date = view.findViewById(R.id.tv_date);

            rate.setRating(Float.parseFloat(m.getRate()));
            money.setText(m.getMoney());
            date.setText(m.getDate());

            return view;
        } else{
            return null;
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
