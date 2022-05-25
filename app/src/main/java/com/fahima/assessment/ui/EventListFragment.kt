package com.fahima.assessment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahima.assessment.R
import com.fahima.assessment.adapter.EventAdapter
import com.fahima.assessment.databinding.FragmentEventListBinding
import com.fahima.assessment.repository.EventRepository
import com.fahima.assessment.util.Constants
import com.fahima.assessment.util.Resource
import com.fahima.assessment.viewmodel.EventViewModel
import com.fahima.assessment.viewmodel.EventViewModelProviderFactory


class EventListFragment : Fragment(R.layout.fragment_event_list) {

    lateinit var viewModel: EventViewModel
    lateinit var eventAdapter: EventAdapter
    lateinit var binding: FragmentEventListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventRepository = EventRepository()
        val viewModelProviderFactory = EventViewModelProviderFactory(activity?.application!!, eventRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[EventViewModel::class.java]
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvEvent.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner, { users ->
            when (users) {
                is Resource.Success -> {
                    eventAdapter.differ.submitList(users.data)
                    hideProgressBar()
                    hideErrorMessage()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    users.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        eventAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.EVENT, it)
            }
            findNavController().navigate(
                R.id.action_eventListFragment_to_eventDetailsFragment,
                bundle
            )
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.tvError.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
    }

}








