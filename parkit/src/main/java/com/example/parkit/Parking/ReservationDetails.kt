package com.example.parkit.Parking

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.parkit.R
import com.example.parkit.database.AppDatabase
import com.example.parkit.databinding.FragmentReservationDetailsBinding
import com.example.parkit.entity.Parking
import com.example.parkit.entity.Reservation
import com.example.parkit.retrofit.Endpoint
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.time.format.DateTimeFormatter
import java.util.*


class ReservationDetails : Fragment() {

    private lateinit var binding: FragmentReservationDetailsBinding
    lateinit var park:Parking
    lateinit var selectedDate: LocalDateTime

    var prix:BigDecimal = (0).toBigDecimal()
    lateinit var hEntree :Date
    lateinit var hSortie: Date

    val viewModel:ParkingViewModel by navGraphViewModels(R.id.fragmentParkings)


    var cal = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReservationDetailsBinding.inflate(layoutInflater)
        val view = binding.root

        //set the default date to now
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy ")
        selectedDate = LocalDateTime.now()
        binding.date.text = selectedDate.format(formatter).toString()
        binding.progressB.visibility = INVISIBLE
        //select date dialog
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                selectedDate = of(year, monthOfYear,dayOfMonth,selectedDate.hour, selectedDate.minute)

            }
        }

        binding.calendar.setOnClickListener(object : View.OnClickListener  {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        OnClickTimeEntree()
        OnClickTimeSortie()

        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        super.onViewCreated(view, savedInstanceState)

        val con = pref.getBoolean("connected", false)
        if (!con)  {
          view.findNavController().navigate(R.id.action_reservationDetails_to_connexionFragment2)
        }
        val selectedPosition = arguments?.getInt("position") as Int
        if(selectedPosition!=null) {
            park = viewModel.getData().get(selectedPosition)
            binding.parking.text = park.nom

            binding.valider.setOnClickListener {
                //TODO check the date > today
                //check time sorite < time entree
                if (prix > (0).toBigDecimal() ){
                    hEntree = Date(selectedDate.year,selectedDate.month.value,selectedDate.dayOfMonth,binding.timePickerEntree.hour,binding.timePickerEntree.minute)
                    hSortie = Date(selectedDate.year,selectedDate.month.value,selectedDate.dayOfMonth,binding.timePickerSortie.hour,binding.timePickerSortie.minute)
                    val userid = pref.getString("id","")!!.toInt()

                    val reservation = Reservation(
                        reservationId= 0,
                        user_Id =userid, parking_Id = park.parkingId,
                        parkingName = park.nom, hEntree = hEntree, hSortie = hSortie,
                        prix = prix.toDouble(), num_place =  Random().nextInt(park.nombre_places))
                    reserver(reservation)
                    val db = AppDatabase.buildDatabase(requireContext());
                    db?.getReservationDao()?.insertreservation(reservation);
                    val bundle = bundleOf("reservationId" to reservation.reservationId)
                    findNavController().navigate(R.id.action_reservationDetails_to_mesReservationDetails,  bundle)
                }else{
                    Toast.makeText(requireContext(),"heures invalides",Toast.LENGTH_SHORT)
                }

            }
        }


    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.date.text = sdf.format(cal.getTime())
    }

    private fun OnClickTimeEntree() {

        binding.timePickerEntree.setOnTimeChangedListener { _, hour, minute ->

            val timeentree = (hour.toDouble() + (minute.toDouble()/60)).toBigDecimal().setScale(1, RoundingMode.UP)
            val timesortie = (binding.timePickerSortie.hour.toDouble() + (binding.timePickerSortie.minute.toDouble()/60)).toBigDecimal().setScale(1, RoundingMode.UP)

            val diff = timesortie - timeentree
            if(diff.toDouble() > 0){
                binding.nbHeure.text = diff.toString()+" heures"
                prix =  (park.tarif * diff.toDouble()).toBigDecimal().setScale(1, RoundingMode.UP)
                binding.prix.text =prix.toString()+" DA"

            }else{
                binding.nbHeure.text = "- heures"
                binding.prix.text = "- DA"
                prix = (0).toBigDecimal()
            }
        }
    }

    private fun OnClickTimeSortie() {

        binding.timePickerSortie.setOnTimeChangedListener { _, hour, minute ->

            val timesortie = (hour.toDouble() + (minute.toDouble()/60)).toBigDecimal().setScale(1, RoundingMode.UP)
            val timeentree = (binding.timePickerEntree.hour.toDouble() + (binding.timePickerEntree.minute.toDouble()/60)).toBigDecimal().setScale(1, RoundingMode.UP)

            val diff = timesortie - timeentree
            if(diff.toDouble() > 0){
                binding.nbHeure.text = diff.toString()+" heures"
                prix =  (park.tarif * diff.toDouble()).toBigDecimal().setScale(1, RoundingMode.UP)
                binding.prix.text =prix.toString()+" DA"

            }else{
                binding.nbHeure.text = "- heures"
                binding.prix.text = "- DA"
                prix = (0).toBigDecimal()

            }
        }

    }

    fun reserver(reservation: Reservation){

        val pref by lazy {
            requireActivity().getSharedPreferences(
                "parkitData",
                AppCompatActivity.MODE_PRIVATE
            )
        }
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progressB.visibility = View.INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.progressB.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().reserver(reservation)
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = View.INVISIBLE
                if ( response.isSuccessful && response.body() != null) {
                  val res:Reservation = response.body()!!
//                    val db = AppDatabase.buildDatabase(requireContext());
//                 db?.getReservationDao()?.insertreservation(res);
//                findNavController().navigate(R.id.action_parking_list_to_details)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "response not successful ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

}


