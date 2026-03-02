package com.zybooks.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.viewmodel.InventoryViewModel;



public class MainActivity extends AppCompatActivity implements SelectItemListener{
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private InventoryViewModel inventoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializes the inventoryViewModel object
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);


        // These two lines create the app bar toolBar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);



        // sets the onclick listener for the floating action button.
        findViewById(R.id.add_new_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditAddItem.class);
                startActivity(intent);
            }
        });


        // initializes recyclerView with layout
        recyclerView = findViewById(R.id.recycler_view);

        // creates a recyclerView layoutManger as a gridLayout with two columns
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        // sets the recyclerViews layout manager with the gridLayout manager
        recyclerView.setLayoutManager(layoutManager);




        //initializes adapter
        adapter = new RecyclerViewAdapter(MainActivity.this, this);
        // starts recycler view
        recyclerView.setAdapter(adapter);

        inventoryViewModel.getInventoryItems().observe(this, items -> {
            adapter.setItems(items);
        });


    } // end of onCreate




    // onCreateOptionsMenu inflates the appbar menu items.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }



    // on OptionsItemSelected handles clicking on the sign in menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.sign_out){

            // When a user clicks on "sign out" these two lines start the login activity
            inventoryViewModel.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }







    // On click listener to open dialog when user clicks on an inventory item.
    @Override
    public void onItemClick(InventoryItem item) {
        Bundle args = new Bundle();


        args.putLong("itemId", item.getId());

        DialogFragment dialog = new InventoryDialogFragment();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "dialog");

    }
}






























