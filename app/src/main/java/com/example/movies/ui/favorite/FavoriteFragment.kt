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
        viewModel.getFavoriteList()

         binding.popularButton.setOnClickListener {
             findNavController().popBackStack()
         }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteFilms.collectLatest(adapter::submitList)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}