package com.example.movies.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movies.app.appComponent
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.di.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        MoviesAdapter(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.viewModelScope.launch {
            viewModel.popularMovies.collectLatest(adapter::submitData)
        }


        adapter.onMoviesItemClickListener = {
            findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it.filmId))
        }


        adapter.onMoviesItemLongClickListener = {
            viewModel.addInFavoriteFilm(it)
        }


    }


    private fun setupRecyclerView() {
        with(binding) {
            rvListMovies.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter(),
                footer = MoviesLoadStateAdapter()
            )

            rvListMovies.adapter = adapter

            adapter.addLoadStateListener {
                rvListMovies.isVisible = it.refresh != LoadState.Loading
                progress.isVisible = it.refresh == LoadState.Loading
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}