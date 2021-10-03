package test.surf.moviedb.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import test.surf.moviedb.R
import test.surf.moviedb.databinding.ActivityMainBinding
import test.surf.moviedb.viewmodel.interfaces.MovieViewModel
import test.surf.moviedb.ui.recyclerview.MovieRVAdapter
import test.surf.moviedb.ui.recyclerview.OnClickMovie
import test.surf.moviedb.utils.ext.observeOnState
import test.surf.moviedb.utils.states.UiState
import test.surf.moviedb.viewmodel.MainViewModel
import test.surf.moviedb.viewmodel.SaveableViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), OnClickMovie {
    private val viewModel: MovieViewModel by viewModels<MainViewModel>()

    private var mainSnackBar: Snackbar? = null
    private var viewStateHelper: ViewStateHelper? = null

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding = ActivityMainBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainSnackBar = Snackbar.make(vB.root, R.string.error_internet, Snackbar.LENGTH_LONG)
        viewStateHelper = ViewStateHelper(vB)

        restoreInstanceState(savedInstanceState)

        initView()
        startObserving()
    }

    private fun initView(){
        vB.pbMain.visibility = View.VISIBLE

        vB.fabReload.setOnClickListener { requestRefreshing() }

        vB.rvMovie.apply{
            adapter = MovieRVAdapter(this@MainActivity, viewModel.data)
                .apply { stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY }
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        vB.swipeRefreshLayout.setOnRefreshListener {
            vB.swipeRefreshLayout.isRefreshing = false
            vB.pbTop.visibility = View.VISIBLE

            requestRefreshing()
        }

        vB.etSearchQuery.setOnEditorActionListener { v, actionId, _ ->
            when (actionId){
                EditorInfo.IME_ACTION_SEARCH ->{
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(vB.root.windowToken, 0)
                    v.clearFocus()

                    requestDataByQuery(v.text.toString())

                    true
                }
                else -> false
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun startObserving(){
        viewModel.uiState.observeOnState(lifecycle){
            when(it.status){
                UiState.Status.UPDATING -> {
                    if (viewModel.data.isEmpty()) {
                        viewStateHelper?.changeStateTo(ViewState.EMPTY_LOADING)
                    } else {
                        viewStateHelper?.changeStateTo(ViewState.LOADING)
                    }
                }
                UiState.Status.SUCCESS -> {
                    viewStateHelper?.changeStateTo(ViewState.LOADED)
                    //При использовании своей пагинации самымй оптимальный способ - notifyItemRangeInserted
                    vB.rvMovie.adapter?.notifyDataSetChanged()

                }
                UiState.Status.FAILED -> {
                    viewStateHelper?.changeStateTo(ViewState.FAILED)
                    mainSnackBar?.setText(R.string.error_internet)?.show()

                    if (viewModel.data.isEmpty()){
                        viewStateHelper?.changeStateTo(ViewState.EMPTY_FAILED)
                        vB.incErrorPanel.tvError.setText(R.string.error_request_failed)
                    }
                }
                UiState.Status.FOUND -> {
                    viewStateHelper?.changeStateTo(ViewState.LOADED)
                    vB.rvMovie.adapter?.notifyDataSetChanged()

                    vB.etSearchQuery.setText(it.msg)

                    if (viewModel.data.isEmpty()){
                        viewStateHelper?.changeStateTo(ViewState.EMPTY_SEARCH)
                        vB.incErrorPanel.tvError.text = getString(R.string.error_search_empty, it.msg)
                    }
                }
                UiState.Status.NOT_FOUND -> {
                    vB.etSearchQuery.setText(it.msg)
                    viewStateHelper?.changeStateTo(ViewState.EMPTY_SEARCH)
                    vB.incErrorPanel.tvError.text = getString(R.string.error_search_empty, it.msg)
                }
            }
        }
    }

    private fun requestRefreshing(){
        viewModel.refreshData()
    }

    private fun requestDataByQuery(query: String){
        viewModel.search(query)
    }


    override fun onClickMovie(pos: Int) {
        val movie = viewModel.data[pos]
        mainSnackBar?.setText(movie.title)?.show()
    }

    override fun onClickFavoriteMovie(pos: Int) {
        val movie = viewModel.data[pos]
        movie.isFavorite = !movie.isFavorite

        viewModel.setMovieIsFavorite(movie.id, movie.isFavorite)

        vB.rvMovie.adapter?.notifyItemChanged(pos)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?){
        (viewModel as? SaveableViewModel)?.let { saveable ->
            savedInstanceState?.let {
                saveable.onRestore(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (viewModel as? SaveableViewModel)?.onSave(outState)
    }

    sealed class ViewState(
        val rvVisibility: Int = View.GONE,
        val pbMainVisibility: Int = View.GONE,
        val pbTopVisibility: Int = View.GONE,
        val incErrorPanelVisibility: Int = View.GONE,
        val fabReloadVisibility: Int = View.GONE,
        val errorRIcon: Int = R.drawable.ic_alert_triangle
    ){
        object LOADING: ViewState(
            pbTopVisibility = View.VISIBLE,
            rvVisibility = View.VISIBLE
        )

        object EMPTY_LOADING: ViewState(
            pbMainVisibility = View.VISIBLE
        )

        object LOADED: ViewState(
            rvVisibility = View.VISIBLE
        )

        object EMPTY_FAILED: ViewState(
            incErrorPanelVisibility = View.VISIBLE,
            fabReloadVisibility = View.VISIBLE
        )

        object FAILED: ViewState(
            rvVisibility = View.VISIBLE
        )

        object EMPTY_SEARCH: ViewState(
            incErrorPanelVisibility = View.VISIBLE,
            errorRIcon = R.drawable.ic_big_search,
            fabReloadVisibility = View.VISIBLE
        )
    }

    inner class ViewStateHelper(private val binding: ActivityMainBinding){
        fun changeStateTo(state: ViewState){
            with(binding){
                rvMovie.visibility = state.rvVisibility
                pbMain.visibility = state.pbMainVisibility
                pbTop.visibility = state.pbTopVisibility
                fabReload.visibility = state.fabReloadVisibility
                incErrorPanel.root.visibility = state.incErrorPanelVisibility
                incErrorPanel.ivIcon.setImageResource(state.errorRIcon)
            }
        }
    }
}