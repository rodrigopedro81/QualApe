package com.navigation

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

fun Fragment.navigateWithRoute(route:String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(Routes.HOME_FRAGMENT_ROUTE.toUri())
        .build()
    findNavController().navigate(request)
}

fun Fragment.navigateWithAction(action: Int) {
    findNavController().navigate(action)
}