package com.zybooks.inventoryapp.model;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


// The Inventory class acts as is annotated with @Entity which when using room
// database allows the clas to act as the database table and a class to create objects
// The @Entity annotation also takes arguments which help define the table.
// The Inventory Entity uses the userId from the User Entity as a foreign key
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id",
        childColumns = "user_id", onDelete = CASCADE))
public class InventoryItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "item")
    private String itemName;

    @ColumnInfo(name = "quantity")
    private int itemQuantity;


    public InventoryItem(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public long getUserId(){
        return userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
