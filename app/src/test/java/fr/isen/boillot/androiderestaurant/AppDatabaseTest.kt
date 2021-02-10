package fr.isen.boillot.androiderestaurant

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.isen.boillot.androiderestaurant.model.database.AppDatabase
import fr.isen.boillot.androiderestaurant.model.database.AppDatabaseDao
import fr.isen.boillot.androiderestaurant.model.database.OrderTable

import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var orderDao: AppDatabaseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        orderDao = db.appDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllOrders() {
        val order = OrderTable(quantity = 1, price = "11", title = "Salade César")
        orderDao.insert(order)
        val allOrders = orderDao.getOrders()
        assertEquals(allOrders?.quantity, 1)
    }
}