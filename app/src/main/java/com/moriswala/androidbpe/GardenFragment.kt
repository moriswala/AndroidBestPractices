package com.moriswala.androidbpe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.moriswala.androidbpe.adapters.GardenPlantingAdapter
import com.moriswala.androidbpe.viewmodels.GardenPlantingListViewModel
import com.moriswala.androidbpe.utilities.InjectorUtils
import com.moriswala.androidbpe.R
import com.moriswala.androidbpe.adapters.PLANT_LIST_PAGE_INDEX
import com.moriswala.androidbpe.databinding.FragmentGardenBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GardenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GardenFragment : Fragment() {

    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}