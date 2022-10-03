package com.delaroystudios.data.users

import com.delaroystudios.data.api.EndPoint
import com.delaroystudios.data.common.InventoryData.createUsersData
import com.delaroystudios.data.common.MainDispatcherRule
import com.delaroystudios.data.model.UsersResponse
import com.delaroystudios.data.utils.UsersFakeData
import com.delaroystudios.data.utils.makeTestApiService
import com.delaroystudios.domain.users.UsersRepository
import com.nhaarman.mockitokotlin2.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import me.tap.data.utils.RequestDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TemporaryFolder

internal class UsersRepositoryImplTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var mockWebServer: MockWebServer
    private val usersCache: UsersCache = mock()

    private lateinit var usersRepository: UsersRepository
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val webDispatcher = RequestDispatcher()

    private val mapper = UsersEntityMapper()

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = webDispatcher
        mockWebServer.start()
        usersRepository = UsersRepositoryImpl(
            api = makeTestApiService(mockWebServer),
            usersCache = usersCache,
            mapper = mapper,
            dispatcher = dispatcherRule.testDispatcher,
        )
    }

    @Test
    fun `check that calling users makes request to given path`() = runTest {
        // when the users method is called
        webDispatcher.shouldReturnError(false)
        usersRepository.fetchUsers()

        // assert that the correct endpoint is called
        Assert.assertEquals("/${EndPoint.USERS}", mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling users makes a GET request`() = runTest {
        // when the users method is called
        webDispatcher.shouldReturnError(false)
        usersRepository.fetchUsers()

        // assert that the correct endpoint is called
        Assert.assertEquals("GET /${EndPoint.USERS} HTTP/1.1", mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that users return a success on request`() = runTest {
        // when the users method is called
        webDispatcher.shouldReturnError(false)
        val result = usersRepository.fetchUsers()

        // assert the result is successful
        Assert.assertTrue(result.isSuccess())
    }

    @Test
    fun `verify that a users is updated successfully`() = runBlocking {
        //given that the users cache returns all the inventory
        whenever(usersCache.getUsers())
            .thenReturn(flowOf(listOf(createUsersData())))

        val resp = 1

        //when we get the users list from the repository
        val result = usersRepository.users.flattenToList()

        //given that users is updated
        whenever(usersRepository.updateUserImage(imageUri = "uri//path-to-image", id = result[0].id))
            .thenReturn(resp)

        val res = usersRepository.updateUserImage(imageUri = "uri//path-to-image", id = result[0].id)


        Assert.assertEquals(resp, res)
    }


    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
