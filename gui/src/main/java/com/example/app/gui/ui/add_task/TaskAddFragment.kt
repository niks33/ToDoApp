package com.example.app.gui.ui.add_task

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.app.base.gui.fragment.BaseFragment
import com.example.app.database.Task
import com.example.app.gui.*
import com.example.app.gui.databinding.FragmentTaskAddBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment class for ${R.layout.fragment_task_add} layout file.
 * */

@AndroidEntryPoint
class TaskAddFragment : BaseFragment() {

    // Implement abstract variable
    override val layoutResId = R.layout.fragment_task_add
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentTaskAddBinding::inflate
    override val binding get() : FragmentTaskAddBinding = super.binding as FragmentTaskAddBinding

    // View model object
    private val viewModel: TaskAddViewModel by viewModels()

    // Time pattern such as 08:00
    private val timePattern = "^(0?[1-9]|1[0-2]):[0-5][0-9]$"

    // To keep check if user is editing time text
    private var isEditing = false

    private val onBackPressed = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!binding.tilTaskTitle.isBlank()) {
                showDiscardAddingTask()
            } else {
                remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressed)
    }

    override fun onInitView() {
        val adapterTimeSuffix = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            requireContext().resources.getStringArray(R.array.time_suffix)
        )

        binding.apply {
            ibBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            (tilTaskTimeSufix.editText as AutoCompleteTextView).setAdapter(adapterTimeSuffix)

            tilTaskTime.editText?.addTextChangedListener(object : TextWatcher {
                val timeEditText = binding.tilTaskTime.editText
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    // Not used in this implementation
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Not used in this implementation
                }

                override fun afterTextChanged(s: Editable?) {
                    if (isEditing) {
                        return
                    }

                    val input = s.toString().trim()
                    if (!isEditing && (s?.length ?: 0) <= 5) {
                        isEditing = true

                        val formattedInput = formatTimeInput(input)
                        if (formattedInput != input) {
                            timeEditText?.setText(formattedInput)
                            timeEditText?.setSelection(formattedInput.length)
                        }
                    }

                    if ((s?.length ?: 0) == 5) {
                        if (!input.matches(timePattern.toRegex())) {
                            timeEditText?.error = getString(R.string.error_invalid_time)
                        } else {
                            timeEditText?.error = null
                        }
                    }
                    isEditing = false
                }
            })
            btnAdd.setOnClickListener {
                hideKeyboard()
                if (validateFields()) {
                    viewModel.addTask(
                        Task(
                            tilTaskTitle.getText(),
                            tilTaskTime.getText(),
                            tilTaskTimeSufix.getText()
                        )
                    )
                    findNavController().navigateUp()
                }
            }
        }
    }

    // To format time input such as 08:00
    private fun formatTimeInput(input: String): String {
        val formattedInput: StringBuilder = StringBuilder(input)
        if (input.length >= 3 && input[2] != ':') {
            formattedInput.insert(2, ':')
        }
        return formattedInput.toString()
    }

    /**
     * To validate input fields. Return a boolean after check
     * true-> if all fields are validated.
     * false -> if field is invalidate. Shows a snack bar with error message
     * */
    private fun validateFields(): Boolean {
        binding.apply {
            var validation = false
            if (tilTaskTitle.isBlank()) {
                binding.root.snack(getString(R.string.error_task_title)) {
                    action(getString(R.string.ok_btn_txt)) {}
                }
            } else if (tilTaskTime.isBlank() || !tilTaskTime.getText()
                    .matches(timePattern.toRegex())
            ) {
                binding.root.snack(getString(R.string.error_task_time)) {
                    action(getString(R.string.ok_btn_txt)) {}
                }
            } else if (tilTaskTimeSufix.isBlank()) {
                binding.root.snack(getString(R.string.error_task_time_suffix)) {
                    action(getString(R.string.ok_btn_txt)) {}
                }
            } else
                validation = true
            return validation
        }
    }

    // To create an alert dialog for confirmation of task deletion.
    private fun showDiscardAddingTask() {
        val dialogBuilder = AlertDialog.Builder(context, R.style.CustomAlertTheme)
        dialogBuilder.apply {
            setMessage(getString(R.string.dialog_msg_add_task_discard))
            setPositiveButton(
                getString(R.string.ok_btn_txt)
            ) { _, _ ->
                onBackPressed.remove()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            setNegativeButton(getString(R.string.cancel_btn_txt)) { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.setTitle(getString(R.string.dialog_title_add_task_discard))
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
    }
}