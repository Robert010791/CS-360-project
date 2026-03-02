package com.zybooks.inventoryapp.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.inventoryapp.model.InventoryItem;

import java.util.List;


@Dao
public interface InventoryItemDao {

    @Query("SELECT * FROM InventoryItem WHERE user_id = :userId")
    LiveData<List<InventoryItem>> getInventoryItems(long userId);

    @Query("SELECT * FROM InventoryItem WHERE id = :itemId")
    InventoryItem getItem(long itemId);

    @Insert
    void addItem(InventoryItem item);

    @Update
    void updateItem(InventoryItem item);

    @Query("DELETE FROM InventoryItem WHERE id = :itemId")
    void deleteItem(long itemId);


}
