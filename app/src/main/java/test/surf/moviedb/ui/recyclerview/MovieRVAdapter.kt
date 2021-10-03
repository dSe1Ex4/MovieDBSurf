package test.surf.moviedb.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.surf.moviedb.BuildConfig
import test.surf.moviedb.R
import test.surf.moviedb.databinding.ItemHolderMovieBinding
import test.surf.moviedb.model.Movie

class MovieRVAdapter(
    private val onClick: OnClickMovie,
    private val values: List<Movie>
) : RecyclerView.Adapter<MovieRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = values[position]
        with(holder){
            val date = if (movie.releaseDate.isNullOrEmpty()) itemView.context.getString(R.string.t_unknown)
                        else movie.releaseDate

            tvTitle.text = movie.title
            tvDesc.text = movie.overview
            tvDate.text = date

            if (movie.posterPath.isNullOrEmpty()){
                // Можно нарисовать свой холдер, когда нет изображений
                // Можно использовать стандартный Glide параметр error
                ivLogo.setImageResource(R.drawable.shape_card_item_holder_movie)
            } else {
                Glide.with(layout)
                    .load(BuildConfig.TMDB_IMG_BASE_URL+movie.posterPath)
                    .into(ivLogo)
            }

            btnHeart.setImageResource(
                if (movie.isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart
            )

            btnHeart.setOnClickListener {
                onClick.onClickFavoriteMovie(position)
            }

            layout.setOnClickListener {
                onClick.onClickMovie(position)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemHolderMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val layout = binding.cardLayout

        val tvTitle = binding.tvName
        val tvDesc = binding.tvDesc
        val ivLogo = binding.ivMovieLogo
        val tvDate = binding.tvDate
        val btnHeart = binding.btnHeart
    }

}