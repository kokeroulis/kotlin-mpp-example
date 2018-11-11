package org.konan.data.factories

import io.ktor.client.HttpClient
import org.konan.data.network.UsersService
import org.konan.data.network.UsersServiceImpl

object UsersServiceFactory {

    val usersService: UsersService by lazy {
        UsersServiceImpl(HttpClient())
    }
}