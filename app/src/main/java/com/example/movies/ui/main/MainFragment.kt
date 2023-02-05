package com.example.movies.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

    private val adapter by lazy {
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
        viewModel.getFavoriteFilmsFlow()
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest(adapter::submitData)
        }



        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.favoriteMovies.collectLatest(adapter::favoriteList)
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


            adapter.onMoviesItemClickListener = {
                findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it.filmId))
            }


            adapter.onMoviesItemLongClickListener = {
                viewModel.addInFavoriteFilm(it)
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}