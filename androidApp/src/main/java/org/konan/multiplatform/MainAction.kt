package org.konan.multiplatform

import org.konan.domain.users.User


sealed class MainAction {

    object LoadMainContent : MainAction()

    data class MainContentLoaded(val content: List<User>) : MainAction()

    object AnotherAction : MainAction()
}