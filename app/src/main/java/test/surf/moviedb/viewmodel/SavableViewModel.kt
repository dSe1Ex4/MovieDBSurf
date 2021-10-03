package test.surf.moviedb.viewmodel

import android.os.Bundle

interface SaveableViewModel {
    fun onSave(bundle: Bundle)
    fun onRestore(bundle: Bundle)
}