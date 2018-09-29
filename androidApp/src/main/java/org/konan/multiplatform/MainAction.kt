package org.konan.multiplatform

sealed class MainAction {

    object LoadMainContent : MainAction()

    data class MainContentLoaded(val content: String) : MainAction()
}