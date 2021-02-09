package fr.isen.boillot.androiderestaurant.model.database

import androidx.room.*

@Dao
interface AppDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: OrderTable)

    @Update
    fun update(order: OrderTable)

    @Delete
    fun delete(order: OrderTable)

    @Query("SELECT * FROM order_table ORDER BY id DESC")
    fun getOrders(): OrderTable?

}