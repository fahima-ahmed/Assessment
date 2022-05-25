package com.fahima.assessment.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fahima.assessment.R
import com.fahima.assessment.databinding.FragmentEventDetailsBinding
import com.fahima.assessment.model.Event
import com.fahima.assessment.util.Constants
import com.fahima.assessment.util.formatTo
import com.fahima.assessment.util.toDate
import com.fahima.assessment.viewmodel.EventDetailsViewModel


class EventDetailsFragment : Fragment(R.layout.fragment_event_details) {

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var binding: FragmentEventDetailsBinding
    private val args: EventDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(args.event)
        setupObservers()
        setupShareButton()
    }

    private fun setupViewModel(event: Event) {
        viewModel = ViewModelProvider(this)[EventDetailsViewModel::class.java]
        viewModel.setEventData(event)
    }

    private fun setupObservers() {
        viewModel.eventDetailData.observe(viewLifecycleOwner, {
            binding.tvTimestamp.text = it.timestamp?.toDate()?.formatTo(Constants.DATE_FORMAT_MMM_DD_YY_HH_MM) ?: it.timestamp
            binding.tvTitle.text = it.title
            binding.tvLocation.text = "${it.locationLine1}, ${it.locationLine2}"
            binding.tvDescription.text = it.description
            Glide.with(this).load(it.image).into(binding.eventImage)
        })

    }

    private fun setupShareButton() {
        binding.shareButton.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            val eventDetail =
                "Event Name: ${viewModel.eventDetailData.value?.title} at ${viewModel.eventDetailData.value?.timestamp}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, eventDetail)
            startActivity(Intent.createChooser(shareIntent, "share via"))
        }
    }

}








