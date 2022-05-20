package com.example.parkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parkit.databinding.FragmentConnexionBinding
import com.example.parkit.databinding.FragmentInscriptionBinding


class Inscription : Fragment() {

    private lateinit var binding: FragmentInscriptionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInscriptionBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nom =  binding.nomPost.text
        val prenom = binding.prenomPost.text
        val email = binding.emailPost.text
        val num = binding.numPost.text
        val pwd = binding.pwdPost.text

    }

}