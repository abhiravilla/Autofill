package com.ravilla.abhi.autofill.Ldispaly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravilla.abhi.autofill.Ldispaly.fulllist;
import com.ravilla.abhi.autofill.R;

import java.util.ArrayList;
import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.listViewHolder>  {

    private ArrayList<fulllist> flist;
    private Context mContext;
    private ArrayList<fulllist> mFilteredList;


    public listAdapter(ArrayList<fulllist> flist, Context mContext) {
        this.flist = flist;
        this.mContext = mContext;
        this.mFilteredList = flist;


    }

    public class listViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ssname;
        public TextView suname;
        public TextView spass;

        public listViewHolder(View view) {
            super(view);
            ssname = (AppCompatTextView) view.findViewById(R.id.ssname);
            suname = (AppCompatTextView) view.findViewById(R.id.suname);
            spass = (AppCompatTextView) view.findViewById(R.id.spass);

        }


        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlelist, parent, false);

        return new listViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final listViewHolder holder, int position) {
        holder.ssname.setText(flist.get(position).getsite());
        holder.suname.setText(flist.get(position).getuname());
        holder.spass.setText(flist.get(position).getpassword());
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

}

