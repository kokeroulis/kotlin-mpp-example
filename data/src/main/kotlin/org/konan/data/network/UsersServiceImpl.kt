package org.konan.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.json.JsonTreeParser
import org.konan.data.users.UserModel

class UsersServiceImpl(
    private val httpClient: HttpClient
) : UsersService {

    override suspend fun getAllUsers(): List<UserModel> {
        val result = httpClient.get<String>("https://jsonplaceholder.typicode.com/users")

        val userList = JsonTreeParser(result).read()
            .jsonArray
            .map { UserModel.fromJsonObject(it.jsonObject) }

        return userList
    }


}