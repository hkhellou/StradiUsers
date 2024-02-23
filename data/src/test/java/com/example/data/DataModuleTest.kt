package com.example.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.di.DataModule
import com.example.data.operations.UsersRetrofit
import com.example.domain.operations.Coordinates
import com.example.domain.operations.Dob
import com.example.domain.operations.Id
import com.example.domain.operations.Location
import com.example.domain.operations.Login
import com.example.domain.operations.Name
import com.example.domain.operations.Picture
import com.example.domain.operations.Registered
import com.example.domain.operations.Street
import com.example.domain.operations.Timezone
import com.example.domain.operations.UserParams
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Retrofit
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DataModuleTest {
    @Inject
    lateinit var mockOkHttpClient: OkHttpClient

    @Inject
    lateinit var mockRetrofit: Retrofit

    @Inject
    lateinit var mockUsersRetrofit: UsersRetrofit

    @Inject
    lateinit var dataModule: DataModule

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun provideOkHttpClient_returnsOkHttpClientWithConfiguredTimeouts() {
        dataModule = DataModule()
        val client = dataModule.provideOkHttpClient()
        assertEquals(60000, client.connectTimeoutMillis())
        assertEquals(60000, client.readTimeoutMillis())
        assertEquals(60000, client.writeTimeoutMillis())
    }

    @Test
    fun provideRetrofit_returnsRetrofitWithCorrectUrl() {
        dataModule = DataModule()
        mockOkHttpClient = OkHttpClient()
        val retrofit: Retrofit = dataModule.provideRetrofit(mockOkHttpClient)
        assertEquals(DataModule.BASE_URL, retrofit.baseUrl().toString())
    }

    @Test
    fun `test provideUsersRetrofit`() {
        dataModule = DataModule()
        mockOkHttpClient = OkHttpClient()
        `when`(mockRetrofit.create(UsersRetrofit::class.java)).thenReturn(mock(UsersRetrofit::class.java))
        val result = dataModule.provideUsersRetrofit(mockRetrofit)

        val expectedUserParams = UserParams(
            gender = "male",
            name = Name(
                title = "Mr",
                first = "Karl",
                last = "Johnson"
            ),
            location = Location(
                street = Street(
                    number = 6057,
                    name = "Avondale Ave"
                ),
                city = "New York",
                state = "New York",
                country = "United States",
                postcode = "12564",
                coordinates = Coordinates(
                    latitude = "88.9222",
                    longitude = "-82.9558"
                ),
                timezone = Timezone(
                    offset = "+3:00",
                    description = "Baghdad, Riyadh, Moscow, St. Petersburg"
                )
            ),
            email = "karl.johnson@example.com",
            login = Login(
                uuid = "97890990-7bf2-469d-981c-16a10ae5307f",
                username = "bigpeacock170",
                password = "luan",
                salt = "DS9jzK3v",
                md5 = "3fbb44cc3ed84f11b0b17d294e648b88",
                sha1 = "bc13b6a687d056cddb6bff9a546d7fcfd03cad45",
                sha256 = "d3dea6c58256857f30dd60ef4db2708b62d5cc37eda19479ed4b72321d543f41"
            ),
            dob = Dob(
                date = "1966-12-17T05:32:24.120Z",
                age = 57
            ),
            registered = Registered(
                date = "2014-12-02T18:39:42.988Z",
                age = 9
            ),
            phone = "(268) 420-4900",
            cell = "(576) 843-3163",
            id = Id(
                name = "SSN",
                value = "557-48-8854"
            ),
            picture = Picture(
                large = "https://randomuser.me/api/portraits/men/6.jpg",
                medium = "https://randomuser.me/api/portraits/med/men/6.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/men/6.jpg"
            ),
            nat = "US"
        )
        assertEquals(expectedUserParams, result)
    }
}