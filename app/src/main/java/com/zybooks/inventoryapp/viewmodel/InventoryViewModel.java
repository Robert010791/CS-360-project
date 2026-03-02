package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.repo.InventoryRepo;

import java.util.List;



// InventoryViewModel gets an instance of the InventoryRepo and then has methods that call
// InventoryViewModel methods. acts as the intermediary between the view and the model
public class InventoryViewModel extends AndroidViewModel {
    private final InventoryRepo inventoryRepo;

    public InventoryViewModel (Application application){
        super(application);
        inventoryRepo = InventoryRepo.getInstance(application.getApplicationContext());
    }

    public void signOut(){
        inventoryRepo.logout();
    }

    public LiveData<List<InventoryItem>> getInventoryItems(){
        return inventoryRepo.getItems();
    }

    public InventoryItem getItem(long id){
       return inventoryRepo.getItem(id);
    }

    public void addNewItem(InventoryItem item){
        inventoryRepo.addItem(item);
    }

    public void updateEditedItem(InventoryItem item){
        inventoryRepo.updateItem(item);
    }

    public void deleteItem(long id){
        inventoryRepo.deleteItem(id);
    }




}
