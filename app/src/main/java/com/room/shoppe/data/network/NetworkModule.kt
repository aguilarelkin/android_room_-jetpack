package com.room.shoppe.data.network

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.room.shoppe.data.database.ProductDatabase
import com.room.shoppe.data.database.RepositoryProductImpl
import com.room.shoppe.data.database.dao.DaoProduct
import com.room.shoppe.domain.repository.RepositoryProduct
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DATABASE_NAME = "shoppeproduct"

    //Create DDBB
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(context, ProductDatabase::class.java, DATABASE_NAME).build()
    }

    //DAO product
    @Singleton
    @Provides
    fun provideDaoProduct(db: ProductDatabase): DaoProduct {
        return db.daoProduct()
    }

    //Product
    @Provides
    fun provideRepositoryProduct(daoProduct: DaoProduct): RepositoryProduct {
        return RepositoryProductImpl(daoProduct)
    }
}