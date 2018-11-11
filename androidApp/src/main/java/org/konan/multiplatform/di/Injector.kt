package org.konan.multiplatform.di

import org.greeting.data.factories.UsersRepositoryFactory
import org.greeting.data.factories.UsersServiceFactory

object Injector {


    fun injectUserRepository() = UsersRepositoryFactory.createUsersRepository(UsersServiceFactory)
}