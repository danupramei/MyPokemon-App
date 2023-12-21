package com.pokemon.shared.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope

fun Fragment.launchWhenResumed(block: suspend (CoroutineScope) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        block.invoke(this)
    }
}

fun Fragment.verticalGridLayoutManager(spanCount: Int) =
    GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)

fun Fragment.onBackPressed() {
    activity?.onBackPressedDispatcher?.onBackPressed()
}

fun Fragment.showToastMessage(message: String) {
    activity?.showToastMessage(message)
}

fun Fragment.navigate(
    route: String,
    extras: Map<String, *>? = null,
    navOptions: NavOptions? = null,
    popBackStack: Boolean = false
) {
    findNavController().apply {
        currentDestination?.let { node ->
            val destinationId = node.id
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != 0) {
                currentNode?.findNode(destinationId)?.run {
                    extras?.let {
                        currentBackStackEntry?.savedStateHandle?.apply {
                            extras.entries.forEach {
                                set(it.key, it.value)
                            }
                        }
                    }
                    if (popBackStack) {
                        currentDestination?.route?.let {
                            popBackStack(it, true)
                        }
                    }
                    navigate(route = route, navOptions = navOptions)
                }
            }
        }
    }
}

fun <T> Fragment.getDataFromPreviousFragment(key: String): T? {
    return findNavController().previousBackStackEntry?.savedStateHandle?.get<T>(key)
}