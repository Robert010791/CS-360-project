package com.zybooks.inventoryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.InventoryViewModel;

import org.w3c.dom.Text;

public class InventoryDialogFragment extends DialogFragment{




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        // Initialize viewModel as the same viewModel used by mainActivity
        InventoryViewModel inventoryViewModel = new ViewModelProvider(requireActivity()).get(InventoryViewModel.class);


        // inflate custom dialog layout
        View view = inflater.inflate(R.layout.inventory_details_fragment, null);

        // Find views from layout and assign them to variables
        TextView itemName = view.findViewById(R.id.dialog_item_name);
        TextView itemQuantity = view.findViewById(R.id.dialog_item_quantity);
        ImageButton increaseQtyBtn = view.findViewById(R.id.increase_count);
        ImageButton decreaseQtyBtn = view.findViewById(R.id.decrease_count);
        ImageButton editItem = view.findViewById(R.id.edit_item);
        ImageButton deleteButton = view.findViewById(R.id.delete_item);


        // Gets the item id that is passed as intent extra
        long itemId = requireArguments().getLong("itemId", -1);



        InventoryItem editInventoryItem = inventoryViewModel.getItem(itemId);
        itemName.setText(editInventoryItem.getItemName());
        itemQuantity.setText(String.valueOf(editInventoryItem.getItemQuantity()));


        // Add custom layout to dialog builder
        builder.setView(view);


        // Set increaseQtyBtn onClickListener to increase count
        increaseQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentValue = Integer.parseInt(itemQuantity.getText().toString());
                currentValue++;
                itemQuantity.setText(String.valueOf(currentValue));
            }
        });

        // Set decreaseQtyBtn onclickListener to decrease count
        decreaseQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentValue = Integer.parseInt(itemQuantity.getText().toString());
                currentValue--;
                itemQuantity.setText(String.valueOf(currentValue));
            }
        });

        // edit buttons onclick starts new activity
        editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), EditAddItem.class);
                intent.putExtra("itemId", itemId);
                startActivity(intent);
                dismiss();
            }
        });

        // delete button onClickListener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inventoryViewModel.deleteItem(itemId);
                dismiss();
            }
        });

        // add positive/save button to dialog builder
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        int updatedQuantity = Integer.parseInt(itemQuantity.getText().toString());


                        editInventoryItem.setItemQuantity(updatedQuantity);
                        inventoryViewModel.updateEditedItem(editInventoryItem);
                    }
                });

        // add negative/cancel button to dialog builder
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                InventoryDialogFragment.this.getDialog().cancel();
            }
        });










        return builder.create();
    }


}
