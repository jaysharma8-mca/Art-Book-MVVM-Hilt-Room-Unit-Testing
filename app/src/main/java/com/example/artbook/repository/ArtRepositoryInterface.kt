package com.example.artbook.repository

import androidx.lifecycle.LiveData
import com.example.artbook.model.ImageResponse
import com.example.artbook.roomdb.Art
import com.example.artbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art:Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}