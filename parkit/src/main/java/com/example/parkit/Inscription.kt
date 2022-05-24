package com.example.parkit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.example.parkit.databinding.FragmentConnexionBinding
import com.example.parkit.databinding.FragmentInscriptionBinding
import com.example.parkit.entity.User
import com.example.parkit.retrofit.Endpoint
import kotlinx.coroutines.*


class Inscription : Fragment() {

    private lateinit var binding: FragmentInscriptionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInscriptionBinding.inflate(layoutInflater)
        val view = binding.root
        binding.progressB.visibility = View.INVISIBLE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nom =  binding.nomPost.text
        val prenom = binding.prenomPost.text
        val email = binding.emailPost.text
        val num = binding.numPost.text
        val pwd = binding.pwdPost.text

        binding.button.setOnClickListener{
            if (nom.isNotBlank() and prenom.isNotBlank() and email.isNotBlank() and num.isNotBlank() and pwd.isNotBlank()) {
                var user = User(0,nom.toString(),prenom.toString(), email.toString(),num.toString(),"",pwd.toString())
                inscription(user)


            } else {
                Toast.makeText(
                    requireContext(),
                    "fields empty ! Please authenticate",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun inscription(user: User){

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
            val response = Endpoint.createEndpoint().inscription(user)
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = View.INVISIBLE
                if ( response.isSuccessful && response.body() != null) {
                    if (response.body() == "201") {

                        pref.edit() {
                            putBoolean("connected", true)
                            putString("email", user.email.toString())
                            putString("pwd" , user.mdp.toString())

                            apply()
                        }
                        findNavController().navigate(R.id.action_inscription2_to_reservationDetails)
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "response not successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}