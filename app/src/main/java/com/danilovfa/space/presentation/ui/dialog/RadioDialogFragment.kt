package com.danilovfa.space.presentation.ui.dialog

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import com.danilovfa.space.R
import com.danilovfa.space.databinding.FragmentDialogRadioBinding
import com.danilovfa.space.utils.extensions.TAG
import com.danilovfa.space.utils.extensions.addGradient

class RadioDialogFragment(
    private val title: String,
    private val radioButtonsText: List<String>,
    private val selectedItem: String = radioButtonsText[0],
    private val onSelectItem: (text: String) -> Unit
) : BaseDialogFragment<FragmentDialogRadioBinding>(FragmentDialogRadioBinding::inflate) {
    companion object {
        fun display(
            fragmentManager: FragmentManager,
            title: String,
            radioButtons: List<String>,
            selectedItem: String = radioButtons[0],
            onSelectItem: (text: String) -> Unit
        ): RadioDialogFragment {
            val dialog = RadioDialogFragment(title, radioButtons, selectedItem, onSelectItem)
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Space_Dialog_Basic)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            titleTextView.text = title
            okTextView.addGradient(requireContext())
            setRadioGroup()
        }
    }

    private fun setRadioGroup() {
        val radioButtons = List(radioButtonsText.size) { position ->
            val radioButton = RadioButton(context)
            radioButton.text = radioButtonsText[position]
            radioButton
        }

        radioButtons.forEach { radioButton ->
            styleRadioButton(radioButton)
            binding.radioGroup.addView(radioButton)
        }

        val selectedItemPosition = radioButtonsText.indexOf(selectedItem)
        val selectedRadioButton = radioButtons[selectedItemPosition]

        binding.radioGroup.check(selectedRadioButton.id)

        binding.okTextView.setOnClickListener {
            val checkedId = binding.radioGroup.checkedRadioButtonId
            val checkedRadioButton = radioButtons.find { radioButton ->
                radioButton.id == checkedId
            }
            val checkedText = checkedRadioButton?.text.toString() ?: ""

            onSelectItem(checkedText)
            dismiss()
        }
    }

    private fun styleRadioButton(radioButton: RadioButton) {
        radioButton.apply {
            textSize = 14f
            setTextColor(context.getColor(R.color.white))
            typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
            buttonTintList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf(-android.R.attr.state_checked)
                ),
                intArrayOf(
                    context.getColor(R.color.yellow),
                    context.getColor(R.color.white)
                )
            )
        }
    }
}