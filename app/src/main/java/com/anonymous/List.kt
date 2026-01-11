package com.anonymous

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anonymous.data.AppDatabase
import com.anonymous.adapters.BeerAdapter2List
import com.anonymous.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.anonymous.data.Beer

/**
 * A simple [Fragment] subclass.
 * Use the [List.newInstance] factory method to
 * create an instance of this fragment.
 */
class List : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var beerAdapter: BeerAdapter2List

    private lateinit var db: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        beerAdapter = BeerAdapter2List(
            onItemClick = { beer ->

            },
            onDeleteClick = { beer ->
                deleteBeer(beer)
            }
        )

        binding.beerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beerAdapter
        }

        db = AppDatabase.getDatabase(requireContext())

        loadBeersFromDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    private fun loadBeersFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val beers = db.beerDao().getAllBeers()
            withContext(Dispatchers.Main) {
                beerAdapter.submitList(beers)
            }
        }
    }

    private fun deleteBeer(beer: Beer) {
        CoroutineScope(Dispatchers.IO).launch {
            db.beerDao().delete(beer)
            val updatedList = db.beerDao().getAllBeers()
            withContext(Dispatchers.Main) {
                beerAdapter.submitList(updatedList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}