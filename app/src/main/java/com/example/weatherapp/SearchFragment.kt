package com.example.weatherapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment :Fragment(R.layout.fragment_search){
    @Inject lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var currentConditions: CurrentConditions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.enableButton.observe(this) { enable ->
            binding.zipCodeButton.isEnabled = enable
        }

        binding.zipCodeEditText.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.toString()?.let { viewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewModel.currentConditions.observe(this){result ->
            currentConditions = result
        }

        binding.zipCodeButton.setOnClickListener {
            try {
                viewModel.loadData()
                val action = SearchFragmentDirections
                    .actionSearchFragmentToCurrentConditionsFragment(currentConditions, viewModel.zipCode.toString())
                findNavController().navigate(action)
            } catch (e: retrofit2.HttpException){
                SubmitErrorDialog().show(childFragmentManager, SubmitErrorDialog.TAG)
            }
        }
    }

}