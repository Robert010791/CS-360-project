package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.InventoryViewModel;

public class EditAddItem extends AppCompatActivity {

    private EditText editName;
    private EditText editQuantity;
    private Button saveEditButton;
    private Button addNewButton;
    private InventoryViewModel inventoryViewModel;
    private InventoryItem editNewInventoryItem;
    private long itemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initializes an instance of inventoryViewModel
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        // Initialize all layout views with their ids
        saveEditButton = findViewById(R.id.edit_save_button);
        addNewButton = findViewById(R.id.add_new_button);
        editName = findViewById(R.id.edit_add_name);
        editQuantity = findViewById(R.id.edit_add_quantity);

        //set the onClickListener for button that creates a new inventory item
        addNewButton.setOnClickListener(this::addNewInventoryItem);
        saveEditButton.setOnClickListener(this::updateEditItem);


        // When model is built the extra that is received from the intent should be the itemId
        Intent intent = getIntent();
        itemId = intent.getLongExtra("itemId", -1);

        // If the itemId retrieved from intent extra is -1 then the user is
        // creating a new item. If the id is not -1 they are editing an item and
        // the editText fields are populate with that items details.
        if (itemId != -1){
                editNewInventoryItem = inventoryViewModel.getItem(itemId);
                editName.setText(editNewInventoryItem.getItemName());
                editQuantity.setText(String.valueOf(editNewInventoryItem.getItemQuantity()));

            saveEditButton.setVisibility(View.VISIBLE);
        } else {
            addNewButton.setVisibility(View.VISIBLE);
        }



        // These two lines create the toolbar appbar.
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        // This if statement enables the up button that lets the user go back to
        // parent screen or in this case the inventory screen
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(itemId == -1){
                getSupportActionBar().setTitle("Add");
            } else{
                getSupportActionBar().setTitle("Edit");

            }
        }


    } // end onCreate



    private void updateEditItem(View view){
        String updatedName = editName.getText().toString();
        int updatedQuantity = Integer.parseInt(editQuantity.getText().toString());

        editNewInventoryItem.setItemName(updatedName);
        editNewInventoryItem.setItemQuantity(updatedQuantity);

        inventoryViewModel.updateEditedItem(editNewInventoryItem);
        finish();
    }





    private void addNewInventoryItem(View view){
        String newItemName = editName.getText().toString();
        String newQuantityText = editQuantity.getText().toString();
        boolean validInput = true;


        // this if block is used to prevent user from submitting blank data
        if(newItemName.isEmpty()){
            editName.setError("Item must have a name");
            validInput = false;
            return;
        } else if(newQuantityText.isEmpty()){
            editQuantity.setError("Item must have a count");
            validInput = false;
            return;
        }

        int newItemQuantity = Integer.parseInt(newQuantityText);

        // If input is valid a new item is add and the activity closes
        if(validInput){
            InventoryItem newItem = new InventoryItem(newItemName, newItemQuantity);
            inventoryViewModel.addNewItem(newItem);
            finish();
        }


    }


}