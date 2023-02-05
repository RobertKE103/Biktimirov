package com.example.movies.ui.search

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.movies.R
import com.example.movies.app.appComponent
import com.example.movies.databinding.FragmentSearchBinding
import com.example.movies.di.viewmodels.ViewModelFactory
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.ui.main.MainFragmentDirections
import com.example.movies.ui.main.MoviesAdapter
import com.example.movies.ui.main.MoviesLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { MoviesAdapter(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.querySearchFilms.doAfterTextChanged {text ->
            viewModel.setQuery(text?.toString() ?: "")
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        with(binding) {
            rvListMovies.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter(),
                footer = MoviesLoadStateAdapter()
            )

            rvListMovies.adapter = adapter

            adapter.onMoviesItemClickListener = {
                findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment(it.filmId))
            }

            adapter.onMoviesItemClickListener = {
                findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it.filmId))
            }

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