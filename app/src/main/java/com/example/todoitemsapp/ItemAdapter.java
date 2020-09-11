package com.example.todoitemsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Responsible for displaying data from the model into the row in the recycler view
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    public interface OnLongClickListener {
        void onItemLongClicked (int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //creating each view
        //use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it inside a View Holder
        return new ViewHolder(todoView);
    }

    //
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //taking data at particular position and putting it in viewHolder
        //grab the item at position and then bind the item into the specified view Holder
        String item = items.get(position);
        holder.bind(item);
    }

    //Tells Recycler view how many items are there in the list
    @Override
    public int getItemCount() { //no. of items available in data
        return items.size();
    }

    //container to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notify the listener which position is long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
