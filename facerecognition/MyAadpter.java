package com.ranjesh.facerecognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class MyAadpter extends RecyclerView.Adapter<MyAadpter.MyViewHolder> {
    Context context;
    ArrayList<User> list;
    public MyAadpter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.etName.setText(user.getName());
        holder.etSurname.setText(user.getSurname());
        holder.etAge.setText(user.getAge());
        holder.etLocation.setText(user.getLocation());
        holder.contact.setText(user.getContact());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView etName , etSurname ,etAge ,etLocation , contact ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etName = itemView.findViewById(R.id.tvfirstName);
            etSurname = itemView.findViewById(R.id.tvsurName);
            etAge = itemView.findViewById(R.id.tvage);
            etLocation = itemView.findViewById(R.id.tvlocation);
            contact = itemView.findViewById(R.id.tvcontact);
        }
    }
}
