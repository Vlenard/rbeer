package com.anonymous

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.anonymous.data.AppDatabase
import com.anonymous.data.Beer
import com.anonymous.data.BeerType
import kotlinx.coroutines.launch

class Edit : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var beer: Beer
    val args: EditArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())

        if (args.beerId == -1L) {
            beer = Beer(
                name = "",
                rating = 0,
                note = "",
                type = BeerType.LAGER
            )
            fillUI()
        } else {
            loadBeerFromDB(args.beerId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    private fun loadBeerFromDB(id: Long){
        lifecycleScope.launch {
            try {
                beer = db.beerDao().getBeerById(id)
                fillUI()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Hiba a sör betöltésekor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillUI() {
        view?.findViewById<TextView>(R.id.etBeerName)?.text = beer.name
        view?.findViewById<TextView>(R.id.etBeerNote)?.text = beer.note
    }
}