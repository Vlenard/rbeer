package com.anonymous

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anonymous.data.AppDatabase
import com.anonymous.data.Beer
import com.anonymous.data.BeerType
import kotlinx.coroutines.launch

class Edit : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var gbeer: Beer
    private lateinit var etBeerName: EditText
    private lateinit var etBeerRate: EditText
    private lateinit var etBeerNote: EditText
    private lateinit var spBeerType: Spinner
    private lateinit var btnSaveBeer: Button
    private lateinit var btnReturn: Button
    val args: EditArgs by navArgs()
    var isNewBeer: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())

        etBeerName = view.findViewById(R.id.etBeerName)
        etBeerRate = view.findViewById(R.id.beerRate)
        etBeerNote = view.findViewById(R.id.etBeerNote)
        spBeerType = view.findViewById(R.id.spBeerType)
        btnSaveBeer = view.findViewById(R.id.btnSaveBeer)
        btnReturn = view.findViewById(R.id.btnReturn)

        isNewBeer = args.beerId == -1L

        if (isNewBeer) {
            gbeer = Beer(
                name = "",
                rating = 0,
                note = "",
                type = BeerType.LAGER
            )
            fillUI()
        } else {
            loadBeerFromDB(args.beerId)
        }

        btnSaveBeer.setOnClickListener {
            val beer = collectBeerFromUi()
            if (beer != null) {
                if (isNewBeer){
                    isNewBeer = false
                    insertBeer2DB(beer)
                } else {
                    updateBeer2DB(beer)
                }
            }
        }

        btnReturn.setOnClickListener {
            findNavController().navigate(R.id.action_edit_to_list)
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
                gbeer = db.beerDao().getBeerById(id)
                fillUI()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Hiba a sör betöltésekor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateBeer2DB(beer: Beer){
        lifecycleScope.launch {
            try {
                db.beerDao().update(beer)
                gbeer = beer
                Toast.makeText(requireContext(), "Sikeres mentés", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Hiba a sör mentésekor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertBeer2DB(beer: Beer){
        lifecycleScope.launch {
            try {
                db.beerDao().insert(beer)
                gbeer = beer
                Toast.makeText(requireContext(), "Sikeres mentés", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Hiba a sör mentésekor", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fillUI() {
        etBeerName.setText(gbeer.name)
        etBeerRate.setText(gbeer.rating.toString())
        etBeerNote.setText(gbeer.note)

        // Spinner kiválasztás BeerType alapján
        spBeerType.setSelection(gbeer.type.ordinal)
    }

    private fun collectBeerFromUi(): Beer? {
        val name = etBeerName.text.toString().trim()
        val note = etBeerNote.text.toString().trim()

        val rating = etBeerRate.text.toString().toIntOrNull()
        if (name.isEmpty() || rating == null || rating !in 1..5) {
            Toast.makeText(
                requireContext(),
                "Név kötelező, értékelés 1–5 között!",
                Toast.LENGTH_SHORT
            ).show()
            return null
        }

        val selectedType = BeerType.values()[spBeerType.selectedItemPosition]

        return Beer(
            id = gbeer.id,
            name = name,
            rating = rating,
            note = note,
            type = selectedType
        )
    }
}