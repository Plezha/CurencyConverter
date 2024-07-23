package com.plezha.mycurrencyconverter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.plezha.mycurrencyconverter.R
import com.plezha.mycurrencyconverter.databinding.ResultFragmentBinding

class ResultFragment : Fragment() {
    private var _binding: ResultFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel> {
        MainActivityViewModel.Factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val chosenCurrencyFrom = mainActivityViewModel.currencies[
            mainActivityViewModel.chosenCurrencyFromIndex!!
        ]
        val chosenCurrencyTo = mainActivityViewModel.currencies[
            mainActivityViewModel.chosenCurrencyToIndex!!
        ]
        binding.textviewCurrencyFrom.text =
            chosenCurrencyFrom.longName
        binding.textviewCurrencyTo.text =
            chosenCurrencyTo.longName
        binding.textviewCurrencyFromAmount.text =
            "${chosenCurrencyFrom.symbol} ${mainActivityViewModel.amount.toString()}"

        mainActivityViewModel.convertedAmount.observe(viewLifecycleOwner) {
            binding.textviewCurrencyToAmount.text = "${chosenCurrencyTo.symbol} $it"
        }
        binding.textviewCurrencyToAmount.text =
            "${chosenCurrencyFrom.symbol} ${0.0}"

        binding.newConversionButton.setOnClickListener {
            mainActivityViewModel.amount = null
            mainActivityViewModel.chosenCurrencyFromIndex = null
            mainActivityViewModel.chosenCurrencyToIndex = null

            findNavController().navigate(R.id.action_ResultFragment_to_InputFragment)
        }
        binding.modifyConversionButton.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_InputFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}