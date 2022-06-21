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
import androidx.navigation.fragment.findNavController
import com.example.parkit.databinding.FragmentConnexionBinding
import com.example.parkit.entity.User
import com.example.parkit.retrofit.Endpoint
import kotlinx.coroutines.*

class Connexion : Fragment() {

    private lateinit var binding: FragmentConnexionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnexionBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progressB.visibility = View.INVISIBLE

        super.onViewCreated(view, savedInstanceState)
        binding.connect.setOnClickListener {
            //TODO("personnalize empty fields warning and refactor for project")
            val mail = binding.editMail.text
            val mdp = binding.editPassword.text
           // TODO("regler cette chose")
            if (mail!!.isNotBlank() and mdp!!.isNotBlank()) {
                var user = User(0,"","", mail.toString(),"","",mdp.toString())
                connexion(user)

                /*if ((mail.toString() == "test@gmail.com") and (mdp.toString() == "test"))
                {
                    pref.edit() {
                        putBoolean("connected", true)
                        putString("email", mail.toString())
                        putString("pwd" , mdp.toString())
                        putInt("id", 1)
                        apply()
                    }
                    it.findNavController().navigate(R.id.action_connexionFragment_to_reservationDetails)
                }else
                {
                    Toast.makeText(requireContext(),"WRONG CREDENTIALS",Toast.LENGTH_SHORT).show()

                }

                 */

            } else {
                Toast.makeText(
                    requireContext(),
                    "fields empty ! Please authenticate",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.inscription.setOnClickListener {
            it.findNavController().navigate(R.id.action_connexionFragment_to_inscription2)

        }


    }

    fun connexion(user: User){

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
            val response = Endpoint.createEndpoint().connexion(user)
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = View.INVISIBLE
                if ( response.body() != null) {
                    if (response.body() == "401") {
                        Toast.makeText(requireActivity(), "mot de passe éronné", Toast.LENGTH_SHORT)
                            .show()
                    } else if (response.body() == "402") {
                        Toast.makeText(
                            requireActivity(),
                            "cet email n'existe pas",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (response.body() == "500") {
                        Toast.makeText(
                            requireActivity(),
                            "internal server error",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        var id = response.body().toString()!!

                            pref.edit() {
                                putBoolean("connected", true)
                                putString("email", user.email.toString())
                                putString("pwd" , user.mdp.toString())
                                putString("id", id)
                                apply()
                            }
                        //TODO change this to the pop and change the graph
                        findNavController().navigate(R.id.action_connexionFragment_to_reservationDetails)
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

