package test.surf.moviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private var _binding: B? = null

    /**
     * View Binding property
     */
    protected val vB get() = _binding!!

    abstract fun getViewBinding(inflater: LayoutInflater) : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding(layoutInflater)
        setContentView(vB.root)
    }
}