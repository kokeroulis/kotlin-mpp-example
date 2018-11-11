package org.konan.multiplatform

import org.greeting.domain.users.User

data class MainState(val isLoading: Boolean = true, val userList: List<User>? = null)