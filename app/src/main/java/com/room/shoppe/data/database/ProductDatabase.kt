package com.room.shoppe.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.room.shoppe.data.database.dao.DaoProduct
import com.room.shoppe.data.database.entities.ProductData

@Database(entities = [ProductData::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun daoProduct(): DaoProduct
}