package com.ranjesh.facerecognition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private List<ComplaintData> complaintList;
    public ComplaintAdapter(List<ComplaintData> complaintList) {
        this.complaintList = complaintList;
    }
    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        ComplaintData complaintData = complaintList.get(position);
        // Set data to TextViews
        holder.tvName.setText("Name: " + complaintData.getName());
        holder.tvSurname.setText("Surname: " + complaintData.getSurname());
        holder.tvAge.setText("Age: " + complaintData.getAge());
        holder.contact.setText("Contact: " + complaintData.getContact());
        holder.tvLocation.setText("Location: " + complaintData.getLocation());
        // Set other data accordingly
        // You can set an onClickListener here if you want to handle clicks on items
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSurname ,tvAge ,tvLocation , contact ;;
        // Add other TextViews for other complaint details
        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            contact = itemView.findViewById(R.id.tvContact);
            // Initialize other TextViews here
        }
    }
}
