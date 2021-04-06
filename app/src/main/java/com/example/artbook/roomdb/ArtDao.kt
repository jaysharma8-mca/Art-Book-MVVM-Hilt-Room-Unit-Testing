package com.example.artbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun deleteArt(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>


    @Query("SELECT EXISTS (SELECT name FROM arts WHERE name = :name AND artistName = :artistName)")
    fun getUserDetails(name: String, artistName: String): Boolean
}