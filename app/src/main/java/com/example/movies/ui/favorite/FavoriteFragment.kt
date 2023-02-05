package com.example.movies.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.app.appComponent
import com.example.movies.databinding.FragmentFavoriteBinding
import com.example.movies.databinding.FragmentMainBinding
import com.example.movies.di.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { FavoriteAdapter(requireContext()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListMoviesFavorite.adapter = adapter

        setupSwipeClickListener(binding.rvListMoviesFavorite)

         binding.popularButton.setOnClickListener {
             findNavController().popBackStack()
         }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteFilms.observe(viewLifecycleOwner){
                adapter.submitList(it)
                Log.d("filListFavorite", it.toString())
            }
        }

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_searchFragment)
        }

    }



    private fun setupSwipeClickListener(rvBusinessList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.bindingAdapterPosition]
                viewModel.deleteFilm(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvBusinessList)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}