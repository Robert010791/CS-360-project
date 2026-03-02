package com.zybooks.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.inventoryapp.model.InventoryItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    private List<InventoryItem> itemList = new ArrayList<>();
    Context context;
    private SelectItemListener listener;



    public RecyclerViewAdapter(Context context, SelectItemListener listener){
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inventory_item, viewGroup, false);

        return new ItemViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){


        // Sets the views with the inventory item name and quantity
        holder.itemName.setText(itemList.get(position).getItemName());
        holder.itemQuantity.setText(String.valueOf(itemList.get(position).getItemQuantity()));


        // Sets the cards color
        int[] colors = context.getResources().getIntArray(R.array.card_colors);
        int colorIndex = position % colors.length;
        int cardColor = colors[colorIndex];
        holder.recyclerCard.setCardBackgroundColor(cardColor);

        // sets the onclick listener for each card
        holder.recyclerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAbsoluteAdapterPosition();
                listener.onItemClick(itemList.get(position));
            }
        });
    }


    @Override
    public int getItemCount(){
        return itemList.size();
    }

    public void setItems(List<InventoryItem> items){
        this.itemList = items;
        notifyDataSetChanged();
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemQuantity;
        CardView recyclerCard;
        LinearLayout contentContainer;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            recyclerCard = itemView.findViewById(R.id.recycler_card_view);
            contentContainer = itemView.findViewById(R.id.card_content);

        }



    }
}
