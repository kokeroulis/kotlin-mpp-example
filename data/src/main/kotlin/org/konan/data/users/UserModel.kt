package org.konan.data.users

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.content
import org.konan.domain.users.User

data class UserModel(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String
) {

    fun mapToUser(): User {
        return User(id, name, userName, email)
    }

    companion object {

        fun fromJsonObject(jsonObject: JsonObject): UserModel {
            return jsonObject.let {
                UserModel(
                    it["id"].content.toInt(),
                    it["name"].content,
                    it["userName"].content,
                    it["email"].content
                )
            }
        }
    }
}

