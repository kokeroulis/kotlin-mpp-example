package org.konan.data.factories

import org.konan.data.users.UserRepositoryImpl
import org.konan.domain.users.UserRepository

object UsersRepositoryFactory {


    fun createUsersRepository(usersServiceFactory: UsersServiceFactory): UserRepository {
        return UserRepositoryImpl(usersServiceFactory.usersService)
    }
}