package com.zybooks.inventoryapp.repo;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zybooks.inventoryapp.model.InventoryItem;
import com.zybooks.inventoryapp.model.User;

@Database(entities = {User.class, InventoryItem.class}, version = 1)
public abstract class InventoryDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract InventoryItemDao inventoryItemDao();
}
