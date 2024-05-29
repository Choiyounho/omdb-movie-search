package com.soten.omdb.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.soten.omdb.databinding.ActivityDetailBinding
import com.soten.omdb.extension.load
import com.soten.omdb.ui.detail.DetailViewModel.Companion.KEY_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews()
        observeData()
    }

    private fun bindViews() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.errorContainer.setOnClickListener {
            detailViewModel.getMovieDetail()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.detailUiState.collect { state ->
                    binding.loadingBar.isVisible = state is DetailUiState.Loading
                    binding.errorContainer.isVisible = state is DetailUiState.Error

                    when (state) {
                        is DetailUiState.Success -> {
                            val movieDetailModel = state.movieDetailModel

                            binding.moviePoster.load(movieDetailModel.poster)
                            binding.movieTitle.text = movieDetailModel.title
                            binding.releaseYear.text = "개봉년도: " + movieDetailModel.year
                            binding.movieActors.text = "배우: " + movieDetailModel.actors
                            binding.moviePlot.text = "줄거리: " + movieDetailModel.plot
                            binding.movieWriter.text = "작가: " + movieDetailModel.writer
                            binding.movieDirector.text = "감독: " + movieDetailModel.director
                            binding.movieGenre.text = "장르: " + movieDetailModel.genre
                            binding.movieRuntime.text = "상영시간: " + movieDetailModel.runtime
                            binding.movieRating.text = "평점: " + movieDetailModel.imdbRating
                        }

                        is DetailUiState.Error -> Unit
                        is DetailUiState.Loading -> Unit
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(context: Context, imdbId: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_ID, imdbId)
            }
        }
    }
}