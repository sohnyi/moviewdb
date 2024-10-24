package com.sohnyi.moviedb.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.sohnyi.moviedb.data.TMDBRepository
import com.sohnyi.moviedb.databinding.ActivityTopRateMoviesBinding
import com.sohnyi.moviedb.model.Movie
import com.sohnyi.moviedb.ui.adapter.LoadStateFooterAdapter
import com.sohnyi.moviedb.ui.adapter.MoviesAdapter
import com.sohnyi.moviedb.viewmodel.TopRateMoviesViewModel
import com.sohnyi.moviedb.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "TopRateMoviesActivity"

class TopRateMoviesActivity : AppCompatActivity() {

    private var _binding: ActivityTopRateMoviesBinding? = null
    private val binding: ActivityTopRateMoviesBinding
        get() = checkNotNull(_binding) {
            "ActivityMainBinding cannot be null"
        }

    private val repository by lazy {
        TMDBRepository()
    }

    private val movieAdapter by lazy {
        MoviesAdapter()
    }

    private val footerAdapter by lazy {
        LoadStateFooterAdapter { movieAdapter.retry() }
    }

    private val combinedAdapter by lazy {
        movieAdapter.withLoadStateFooter(footerAdapter)
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(this, repository)
        )[TopRateMoviesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTopRateMoviesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.rvMovies.adapter = combinedAdapter
        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        binding.bindUI(
            adapter = movieAdapter,
        )

    }


    private fun ActivityTopRateMoviesBinding.bindUI(
        adapter: MoviesAdapter,
    ) {
        bindList(
            adapter = adapter,
        )

        bindState(adapter = adapter)
    }


    private fun ActivityTopRateMoviesBinding.bindState(
        adapter: MoviesAdapter,
    ) {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                Log.i(TAG, "bindState: loadStateFlow state: $state")
                progressCircular.isVisible = state.refresh is LoadState.Loading
                btnRetry.isVisible = state.refresh is LoadState.Error

                tvEmpty.isVisible =
                    adapter.itemCount == 0
                            && state.refresh is LoadState.NotLoading


            }
        }

        btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun ActivityTopRateMoviesBinding.bindList(
        adapter: MoviesAdapter,
    ) {

        viewModel.getTopRatedMovies().observe(this@TopRateMoviesActivity) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }


        /*lifecycleScope.launch {
            while (true) {
                withContext(Dispatchers.IO) {
                    delay(5000)
                }
            }
        }*/
    }

}