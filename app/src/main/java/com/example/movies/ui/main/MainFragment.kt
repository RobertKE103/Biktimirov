package com.example.movies.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
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
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest(adapter::submitData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteMovies.observe(viewLifecycleOwner, adapter::favoriteList)
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.isVisible.collectLatest(this@MainFragment::setupVisibleButtons)
        }

        binding.favoriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoriteFragment)
        }

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
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
                Toast.makeText(requireContext(), it.nameRu, Toast.LENGTH_LONG).show()
            }



            rvListMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val manager = (recyclerView.layoutManager) as LinearLayoutManager

                    if (dy > 10 || dy < -10) {
                        viewModel.setupVisibleET(false)
                    } else if (dy == 1 || dy == -1 || manager.findFirstVisibleItemPosition() == 0) {
                        viewModel.setupVisibleET(true)
                    }
                }
            })

        }
    }


    private fun setupVisibleButtons(itVisible: Boolean) {
        if (itVisible) {
            binding.popularButton.visibility = View.VISIBLE
            binding.favoriteButton.visibility = View.VISIBLE
        } else {
            binding.popularButton.visibility = View.INVISIBLE
            binding.favoriteButton.visibility = View.INVISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}