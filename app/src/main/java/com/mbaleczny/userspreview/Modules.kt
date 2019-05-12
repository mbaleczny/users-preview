package com.mbaleczny.userspreview

import com.mbaleczny.userspreview.data.remote.service.ApiFactory
import com.mbaleczny.userspreview.data.remote.service.RetrofitFactory
import com.mbaleczny.userspreview.data.repository.UserRepository
import com.mbaleczny.userspreview.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Mariusz Baleczny
 * @date 12/05/19
 */

val modules = module {

    factory { RetrofitFactory() }

    factory { ApiFactory(get()) }

    factory { (get() as ApiFactory).github() }

    factory { (get() as ApiFactory).dailymotion() }

    single { UserRepository(get(), get()) }

    viewModel { UserViewModel(get()) }

}