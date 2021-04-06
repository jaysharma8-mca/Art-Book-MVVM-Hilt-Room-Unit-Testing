package com.example.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.artbook.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var dao : ArtDao

    @Before
    fun setup(){
        /*database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()*/
        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTest() = runBlocking{
        val exampleArt = Art("Mona Lisa","Da Vinci",1800, "test.com",1)
        dao.insertArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTest() = runBlocking{
        val exampleArt = Art("Mona Lisa","Da Vinci",1800, "test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val list = dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }

    @Test
    fun getSelectedArtTest() = runBlocking{
        val exampleArt = Art("Mona Lisa","Da Vinci",1800, "test.com",1)
        dao.insertArt(exampleArt)
        val userDetails = dao.getUserDetails("Mona Lisa","Da Vinci")
        println(userDetails)
        assertThat(userDetails).isTrue()

    }
}