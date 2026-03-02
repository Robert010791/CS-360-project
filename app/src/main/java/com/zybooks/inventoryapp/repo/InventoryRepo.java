package com.zybooks.inventoryapp.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.zybooks.inventoryapp.SessionManger;
import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {
    private static InventoryRepo inventoryRepo;

    // Create variable for InventoryDAO and UserDao
    private final InventoryItemDao inventoryItemDao;
    private final UserDao userDao;

    // A session manager class is used to store a users id which keeps them
    // logged in even if the user closes the application.
    SessionManger sessionManger;


    // Singleton pattern is used for the inventory repository. getInstance
    // creates a new InventoryRepo object if one doesn't exist or
    // it returns the one instance where ever the getInstance is called.
    public static InventoryRepo getInstance(Context context){
        if (inventoryRepo == null){
            inventoryRepo = new InventoryRepo(context);
        }
        return inventoryRepo;
    }


    // Constructor is private and can only be created with the getInstance method.
    // The constructor creates the database and initializes the DAO objects
    // which are used to interact with the database
    private InventoryRepo(Context context) {
        InventoryDatabase database = Room.databaseBuilder(context, InventoryDatabase.class,
                "inventory.db").allowMainThreadQueries().build();

        inventoryItemDao = database.inventoryItemDao();
        userDao = database.userDao();

        sessionManger = new SessionManger(context);
    }




    // Used to create a new User method accessed from UserViewModel
    public boolean createUser(User newUser){
        if(userDao.checkForName(newUser.getUserName()) == null){
            long userId = userDao.createUser(newUser);
            sessionManger.createLoginSession(userId);
            return true;
        }
        return false;
    }


    // User to log a user in accessed from UserViewModel
    public boolean login(User user){
        User currentUser = userDao.getUser(user.getUserName(), user.getPassword());

        if (currentUser != null){
            long userId = currentUser.getUserId();
            sessionManger.createLoginSession(userId);
            return true;
        }
        return false;
    }

    // Logs the user out by using the sessionManger logout() method.
    public void logout(){
        sessionManger.logout();
    }


    // Uses sessionManger to get the users id and then sets the new items user id.
    public void addItem(InventoryItem item){
        item.setUserId(sessionManger.getUserId());
        inventoryItemDao.addItem(item);

    }



    // getItems returns all items associated to the id returned with sessionManger
    public LiveData<List<InventoryItem>> getItems(){
        return inventoryItemDao.getInventoryItems(sessionManger.getUserId());
    }

    // Used to update which is called from the detail dialog and the edit/add screen
    public void updateItem(InventoryItem updatedItem){
        inventoryItemDao.updateItem(updatedItem);
    }


    //Gets one inventory item using itemId
    public InventoryItem getItem(long id){
        return inventoryItemDao.getItem(id);
    }

    // deletes item with given item id
    public void deleteItem(long itemId){
       inventoryItemDao.deleteItem(itemId);
    }






}
