package com.zybooks.inventoryapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.model.User;
import com.zybooks.inventoryapp.repo.InventoryRepo;


// UserViewModel gets and instance of the InventoryRepo and acts as
// intermediary between view and model
public class UserViewModel  extends AndroidViewModel {

    private final InventoryRepo inventoryRepo;

    public UserViewModel (Application application){
        super(application);
        inventoryRepo = InventoryRepo.getInstance(application);
    }

    public boolean loginUser(User user){
        return inventoryRepo.login(user);
    }

    public boolean createUser(User newUser){
        return inventoryRepo.createUser(newUser);
    }



}
