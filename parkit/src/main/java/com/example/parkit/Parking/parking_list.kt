package com.example.parkit.Parking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkit.Adapter
import com.example.parkit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior


class parking_list : Fragment() {
    private  var recyclerView: RecyclerView? = null
    private lateinit var arrayList: ArrayList<Parking>

    lateinit var images  :Array<Int>
    lateinit var etats : Array<String>
    lateinit var occupations : Array<String>
    lateinit var noms : Array<String>
    lateinit var adresses : Array<String>
    lateinit var distances : Array<String>
    lateinit var temps_trajets : Array<String>
    lateinit var latitude : Array<Double>
    lateinit var longitude : Array<Double>
    lateinit var h_ouv : Array<String>
    lateinit var h_ferm : Array<String>
    lateinit var tarif : Array<Double>
    lateinit var note : Array<Double>

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.parking_list)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{
        val _view = inflater.inflate(R.layout.fragment_parking_list, container, false)

        var sheet = requireActivity().findViewById(R.id.sheet) as? FrameLayout


        if (sheet != null) {
            BottomSheetBehavior.from(sheet).apply{
                peekHeight = 500
                this.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

//        images = arrayOf(
//            R.drawable.parking1,
//            R.drawable.parking2,
//            R.drawable.parking3,
//            R.drawable.parking4,
//            R.drawable.parking1,
//            R.drawable.parking2,
//            R.drawable.parking3,
//            R.drawable.parking4
//        )
//        etats = arrayOf("fermé","ouvert","fermé","ouvert","fermé","ouvert","fermé","ouvert")
//        occupations = arrayOf("90%","35%","47%","12%","90%","35%","47%","12%")
//        noms = arrayOf("Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400", "Parking du marché", "Parking P14","Paking communal 100 places", "Parking H400")
//        adresses = arrayOf("Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida","Bab Ezzeouar","Dar LBeida")
//        distances = arrayOf("8,56 Km","12.4 Km","7,92 Km","12,7 Km","8,56 Km","12.4 Km","7,92 Km","12,7 Km")
//        temps_trajets = arrayOf("14 min", "17 min","12 min","16 min","14 min", "17 min","12 min","16 min")
//        note = arrayOf<Double>(3.3, 4.2, 2.7, 5.0, 1.8,4.2, 2.7, 5.0, 1.8 )
//        tarif = arrayOf<Double>(100.0, 120.0,250.0, 150.0, 100.0, 120.0,250.0, 150.0)
//        h_ouv = arrayOf("08h00","09h00","10h00","07h00","08h00","09h00","10h00","07h00")
//        h_ferm = arrayOf("18h00","19h00","22h00","23h00","21h00","19h00","23h00","23h30")
//        latitude = arrayOf(36.679484,36.679484,36.679484,36.679484,36.679484,36.679484,36.679484,36.679484)
//        longitude = arrayOf(3.138896,3.138896,3.138896,3.138896,3.138896,3.138896,3.138896,3.138896)


        recyclerView = _view.findViewById(R.id.recyclerView) as? RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(activity)


        arrayListOf<Parking>().also { arrayList = it }

       // getData()
        return _view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    private fun getData(){
        for (i in viewModel.images.indices){
            val parking = Parking(viewModel.images[i],viewModel.latitude[i], viewModel.longitude[i],
                viewModel.etats[i],viewModel.occupations[i],viewModel.noms[i],viewModel.adresses[i],
                viewModel.distances[i],viewModel.temps_trajets[i],viewModel.note[i], viewModel.h_ouv[i],
                viewModel.h_ferm[i], viewModel.tarif[i])
            arrayList.add(parking)
            print("added $i");
        }

        recyclerView?.adapter = Adapter(arrayList)
    }

}