package org.konan.data.users

import org.konan.data.network.UsersService
import org.konan.domain.users.User
import org.konan.domain.users.UserRepository

class UserRepositoryImpl(
    private val usersService: UsersService
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return usersService.getAllUsers().map { it.mapToUser() }
    }
}