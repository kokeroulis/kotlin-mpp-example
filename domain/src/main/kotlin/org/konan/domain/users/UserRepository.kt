package org.konan.domain.users

interface UserRepository {

    suspend fun getUsers(): List<User>
}