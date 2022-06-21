package com.example.parkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.parkit.databinding.FragmentInscriptionBinding
import com.example.parkit.databinding.FragmentProfilBinding


class Profil : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater)
        val pref by lazy {
            requireActivity().getSharedPreferences(
                "parkitData",
                AppCompatActivity.MODE_PRIVATE
            )
        }
        TODO("get the local infos of the logged in person et replace the text in the textfields")
        // binding.editMail.text = pref.getString("email")
        // binding.editName.text = pref.getString("nom")
        // binding.editNumPhone.text = pref.getString("numero")


        val view = binding.root

        return view
    }


}