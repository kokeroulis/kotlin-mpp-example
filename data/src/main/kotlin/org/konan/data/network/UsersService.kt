package org.konan.data.network

import org.konan.data.users.UserModel


interface UsersService {

    suspend fun getAllUsers(): List<UserModel>
}