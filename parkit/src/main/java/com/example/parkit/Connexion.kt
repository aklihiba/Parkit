package com.example.parkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.example.parkit.databinding.FragmentAuthBinding
import com.example.parkit.databinding.FragmentConnexionBinding

class Connexion : Fragment() {

    private lateinit var binding: FragmentConnexionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConnexionBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }
        super.onViewCreated(view, savedInstanceState)
        binding.connect.setOnClickListener{
            //TODO("add case empty fields and refactor for project")
            val mail = binding.editMail.text
            val mdp = binding.editPassword.text
            if(mail.isNotBlank() and mdp.isNotBlank()) {
                if (mail.equals("test@gmail.com") and mdp.equals("test"))
                {
                    pref.edit() {
                        putBoolean("connected", true)
                        putString("email", mail.toString())
                        putString("pwd" , mdp.toString())
                        apply()
                    }
                    it.findNavController().navigate(R.id.action_connexionFragment_to_details)
                }else
                {
                    Toast.makeText(requireContext(),"WRONG CREDENTIALS",Toast.LENGTH_SHORT).show()

                }

            }else {
                Toast.makeText(requireContext(),"fields empty ! Please authenticate",Toast.LENGTH_SHORT).show()
            }
        }


    }
}
