package com.example.parkit.Parking

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.parkit.databinding.FragmentMesReservationDetailsBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder


class MesReservationDetails : Fragment() {

    lateinit var binding : FragmentMesReservationDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMesReservationDetailsBinding.inflate(inflater)
        val view = binding.root

        val resId = arguments?.getInt("reservationId") as Int
        val bitmap = generateQR(resId.toString(), 512)

        binding.imageView3.setImageBitmap(bitmap)

        return view
    }

    fun generateQR(content: String?, size: Int): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.encodeBitmap(
                content,
                BarcodeFormat.QR_CODE, size, size
            )
        } catch (e: WriterException) {
            Log.e("generateQR()", e.message.toString())
        }
        return bitmap
    }
//
//    val db = AppDatabase.buildDatabase(requireContext());
//    val reservations = binding.reservations as TextView
//    val reservation = db?.getReservationDao()?.getAllReservations().toString()
//    reservations.text = reservation?.toString()
}