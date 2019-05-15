package com.mbaleczny.userspreview.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbaleczny.userspreview.data.User
import com.mbaleczny.userspreview.data.repository.Resource
import com.mbaleczny.userspreview.data.repository.UserRepository
import com.mbaleczny.userspreview.util.CoroutinesDispatcherProvider
import com.mbaleczny.userspreview.utils.CoroutinesMainDispatcherRule
import com.mbaleczny.userspreview.utils.testObserver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * @author Mariusz Baleczny
 * @date 13/05/19
 */
@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    @get:Rule
    val executor = InstantTaskExecutorRule()

    private val repository: UserRepository = mockk()

    @Test
    fun loadUsers_successfullyReturnsListOfUsers() {
        // given
        val users = listOf(User("Chris", "some.url", "github.com"))

        val viewModel = createViewModel(Resource(users))

        // when
        viewModel.loadUsers()

        // then
        coVerify { repository.getUsers() }

        val usersObserver = viewModel.getUsers().testObserver()
        Assert.assertEquals(users, usersObserver.observedValues.flatten())
        Assert.assertEquals(false, viewModel.isLoading().value)
        Assert.assertEquals(false, viewModel.isError().value)
    }

    @Test
    fun loadUsers_returnsError() {
        // given
        val users = emptyList<User>()
        val viewModel = createViewModel(Resource(null, error = true))

        val usersObserver = viewModel.getUsers().testObserver()

        // when
        viewModel.loadUsers()

        // then
        coVerify { repository.getUsers() }

        Assert.assertEquals(users, usersObserver.observedValues.flatten())
        Assert.assertEquals(false, viewModel.isLoading().value)
        Assert.assertEquals(true, viewModel.isError().value)
    }

    private fun createViewModel(
        result: Resource<List<User>> = Resource(emptyList())
    ): UserViewModel = runBlocking {
        coEvery { repository.getUsers() } returns result
        return@runBlocking UserViewModel(repository, provideFakeCoroutinesDispatcherProvider())
    }

    private fun provideFakeCoroutinesDispatcherProvider() =
        CoroutinesDispatcherProvider(Dispatchers.Unconfined, Dispatchers.Unconfined, Dispatchers.Unconfined)
}