package test.surf.moviedb.utils.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.observeOnState(lifecycle: Lifecycle, state: Lifecycle.State = Lifecycle.State.STARTED, onEach: (T) -> Unit){
    lifecycle.coroutineScope.launch {
        lifecycle.repeatOnLifecycle(state){
            collect {
                onEach.invoke(it)
            }
        }
    }
}