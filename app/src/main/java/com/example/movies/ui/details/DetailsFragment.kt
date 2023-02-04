package com.example.movies.ui.details

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.app.appComponent
import com.example.movies.databinding.FragmentDetalsBinding
import com.example.movies.di.viewmodels.ViewModelFactory
import com.example.movies.domain.entity.details.DetailsResponse
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
    }

    private val moviesId by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetalsBinding? = null
    private val binding get() = _binding!!


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovie(moviesId.id)
        Log.d("testId", moviesId.id.toString())

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }


        viewModel.film.observe(viewLifecycleOwner){
            val data = it?.body()
            setupView(data)
        }


    }



    private fun setupView(data: DetailsResponse?){

        with(binding){
            Glide.with(requireContext())
                .load(data?.posterUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(img)


            name.text = data?.nameRu
            description.text = data?.description


            genre.text = Html.fromHtml(
                String.format(getString(R.string.str_genres),
                data?.genres?.joinToString(", ") {it.genre}), Html.FROM_HTML_MODE_COMPACT
            )

            country.text = Html.fromHtml(String.format(getString(R.string.str_country), data?.countries?.joinToString ( ", " ) {it.country}))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}