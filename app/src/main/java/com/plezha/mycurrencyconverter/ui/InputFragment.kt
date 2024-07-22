package com.plezha.mycurrencyconverter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.plezha.mycurrencyconverter.R
import com.plezha.mycurrencyconverter.databinding.InputFragmentBinding

class InputFragment : Fragment() {
    private var _binding: InputFragmentBinding? = null

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
        _binding = InputFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.currencyAmountEdittext.setText(
            (mainActivityViewModel.amount ?: "").toString()
        )
        binding.edittextFrom.text = getCurrencyName(mainActivityViewModel.chosenCurrencyFromIndex)
        binding.edittextTo.text = getCurrencyName(mainActivityViewModel.chosenCurrencyToIndex)

        binding.edittextFrom.setCurrenciesPopupMenu {
            mainActivityViewModel.chosenCurrencyFromIndex = it
        }
        binding.edittextTo.setCurrenciesPopupMenu {
            mainActivityViewModel.chosenCurrencyToIndex = it
        }

        binding.currencyAmountEdittext.doAfterTextChanged {
            try {
                mainActivityViewModel.amount = it.toString().toDouble()
            } catch (nfe: NumberFormatException) {
                binding.currencyAmountEdittext.setText(mainActivityViewModel.amount.toString())
            }
        }
        binding.convertButton.setOnClickListener {
            try {
                mainActivityViewModel.getConvertedCurrencyAmount()
                findNavController().navigate(R.id.action_InputFragment_to_ResultFragment)
            } catch (ise: IllegalStateException) {
                Toast.makeText(
                    activity, getString(R.string.inputUnfilledFieldsError), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getCurrencyName(index: Int?): String {
        if (index == null) return ""
        return mainActivityViewModel.currencies[index].name
    }

    private fun TextView.setCurrenciesPopupMenu(setChosenIndex: (Int?) -> Unit ) {
        setOnClickListener {
            val menu = PopupMenu(activity, this)
            menu.setOnMenuItemClickListener {
                this.text = it.title
                val index = mainActivityViewModel.currencies.indexOfFirst { element ->
                    element.name == it.title
                }
                setChosenIndex(if (index == -1) null else index)
                true
            }
            mainActivityViewModel.currencies.forEach {
                menu.menu.add(it.name)
            }
            menu.show()
        }
    }
}