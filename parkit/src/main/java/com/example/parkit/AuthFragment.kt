package com.example.parkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.parkit.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {

    private lateinit var binding:FragmentAuthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inscription.setOnClickListener{
            it.findNavController().navigate(R.id.action_authFragment_to_inscriptionFragment)

        }
        binding.connexion.setOnClickListener{
            it.findNavController().navigate(R.id.action_authFragment_to_connexionFragment)

        }

    }

}