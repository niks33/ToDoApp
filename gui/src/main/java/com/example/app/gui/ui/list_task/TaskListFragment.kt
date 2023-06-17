package com.example.app.gui.ui.list_task

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.app.base.gui.fragment.BaseFragment
import com.example.app.base.gui.observe
import com.example.app.database.Task
import com.example.app.gui.R
import com.example.app.gui.databinding.FragmentTaskListBinding
import com.example.app.gui.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * A fragment class for ${R.layout.fragment_task_list} layout file.
 * */

@AndroidEntryPoint
class TaskListFragment : BaseFragment() {

    // Implement abstract variable
    override val layoutResId = R.layout.fragment_task_list
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentTaskListBinding::inflate
    override val binding get() : FragmentTaskListBinding = super.binding as FragmentTaskListBinding

    // Injecting recycle view adapter
    @Inject
    internal lateinit var taskListAdapter: TaskListAdapter

    // View model object
    private val viewModel: TaskListViewModel by viewModels()

    // List of sorting types
    private val sortTypes = arrayOf("Title", "Created", "Status")

    override fun onInitViewModel() {
        viewModel.getAllTask()
    }

    override fun onInitView() {
        Timber.d("onInitView IN")
        viewModel.apply {
            observe(task) {
                binding.apply {
                    tvEmptyListMsg.isVisible = it.isEmpty()
                }
                taskListAdapter.apply {
                    taskList = it
                    sortTaskList()
                }
            }
        }


        binding.apply {
            rvTask.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                adapter = taskListAdapter.apply {
                    onCrossClickListener = {
                        showDeleteTaskDialog(it)
                    }
                    onCheckClickListener = { task, isChecked ->
                        task.id?.let {
                            viewModel.updateTask(it, isChecked)
                        }
                    }
                }
            }

            fabAddTask.setOnClickListener {
                findNavController().navigate(TaskListFragmentDirections.toTaskAddFragment())
            }

            ibOptions.setOnClickListener {
                showSortTaskDialog()
            }

            ibBack.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

   // To create an alert dialog for confirmation of task deletion.
    private fun showDeleteTaskDialog(task: Task) {
        val dialogBuilder = AlertDialog.Builder(context, R.style.CustomAlertTheme)
        dialogBuilder.apply {
            setMessage(getString(R.string.dialog_msg_task_deletion, task.title))
            setPositiveButton(
                getString(R.string.ok_btn_txt)
            ) { _, _ -> task.id?.let {
                viewModel.deleteTask(it)
            } }
            setNegativeButton(getString(R.string.cancel_btn_txt)) { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.setTitle(getString(R.string.dialog_title_task_deletion))
        alertDialog.show()
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
    }

    // To create an alert dialog for sorting of task.
    private fun showSortTaskDialog() {
        val dialogBuilder = AlertDialog.Builder(context, R.style.CustomAlertTheme)

        val checkedItem = (requireActivity() as MainActivity).sortType
        dialogBuilder.setSingleChoiceItems(
            sortTypes, checkedItem
        ) { dialog, selection ->
            sortTaskList(selection)
            dialog?.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.setTitle(getString(R.string.dialog_title_task_sort))
        alertDialog.show()
    }

    /**
     * To sort task list inside rvTask recycle view.
     * @param -> Int: This integer can be the selected location of item inside ${sortTypes} list of
     *           from ${sortType} integer value on MainActivity
     * Based on param, call adapter's sorting method and save passed sortType to MainActivity
     * */
    private fun sortTaskList(sortType: Int = (requireActivity() as MainActivity).sortType) {
        taskListAdapter.apply {
            when (sortType) {
                0 -> sortByTitle()
                1 -> sortById()
                2 -> sortByStatus()
            }
        }
        (requireActivity() as MainActivity).sortType = sortType
    }

}