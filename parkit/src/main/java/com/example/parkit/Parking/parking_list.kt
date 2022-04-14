package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Adapter




class parking_list : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Parking>
    lateinit var images  :Array<Int>
    lateinit var etats : Array<String>
    lateinit var occupations : Array<String>
    lateinit var noms : Array<String>
    lateinit var adresses : Array<String>
    lateinit var distances : Array<String>
    lateinit var temps_trajets : Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment,container,false)
    }

    private fun getData(){
        for (i in images.indices){
            val parking = Parking(images[i],etats[i],occupations[i],noms[i],adresses[i],distances[i],temps_trajets[i])
            arrayList.add(parking)
        }

        recyclerView.adapter = Adapter(arrayList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        images = arrayOf(
            R.drawable.parking1,
            R.drawable.parking2,
            R.drawable.parking3,
            R.drawable.parking4,
            R.drawable.parking1,
            R.drawable.parking2,
            R.drawable.parking3,
            R.drawable.parking4
        )
        etats = arrayOf("fermé","ouvert","fermé","ouvert","fermé","ouvert","fermé","ouvert")
        occupations = arrayOf("90%","35%","47%","12%","90%","35%","47%","12%")
        noms = arrayOf("Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400", "Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400")
        adresses = arrayOf("Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida")
        distances = arrayOf("8,56 Km","12.4 Km","7,92 Km","12,7 Km","8,56 Km","12.4 Km","7,92 Km","12,7 Km")
        temps_trajets = arrayOf("14 min", "17 min","12 min","16 min","14 min", "17 min","12 min","16 min")

        recyclerView = requireActivity().findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        arrayListOf<Parking>().also { arrayList = it }

        getData()

    }

}