package com.domain

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

fun Fragment.navigateWithRoute(route:String) {
    val request = NavDeepLinkRequest.Builder
        .fromUri(route.toUri())
        .build()
    findNavController().navigate(request)
}

fun Fragment.navigateWithAction(action: Int) {
    findNavController().navigate(action)
}