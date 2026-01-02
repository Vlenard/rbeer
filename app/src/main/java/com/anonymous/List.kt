package com.anonymous

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.data.BeerAdapter2List
import com.anonymous.data.BeerType
import com.anonymous.data.Beer
import com.anonymous.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [List.newInstance] factory method to
 * create an instance of this fragment.
 */
class List : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var beerAdapter: BeerAdapter2List

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentListBinding.bind(view)

        val sampleBeers = listOf(
            Beer(
                id = 1,
                name = "Soproni Classic",
                rating = 4,
                note = "Klasszikus magyar lager, könnyen iható.",
                type = BeerType.STOUT
            ),
            Beer(
                id = 2,
                name = "Mad Scientist IPA",
                rating = 5,
                note = "Erősen komlós, gyümölcsös illattal.",
                type = BeerType.IPA
            ),
            Beer(
                id = 3,
                name = "Guinness Draught",
                rating = 5,
                note = "Krémes stout, pörkölt malátás íz.",
                type = BeerType.STOUT
            ),
            Beer(
                id = 4,
                name = "Paulaner Hefe-Weißbier",
                rating = 4,
                note = "Banános, szegfűszeges búzasör.",
                type = BeerType.IPA
            ),
            Beer(
                id = 5,
                name = "Pilsner Urquell",
                rating = 3,
                note = "Kesernyés, tiszta ízvilág.",
                type = BeerType.LAGER
            )
        )


        beerAdapter = BeerAdapter2List { beer ->

        }

        view.findViewById<RecyclerView>(R.id.beerList).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = beerAdapter
        }

        // IDEIGLENES tesztadat
        beerAdapter.submitList(sampleBeers)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}