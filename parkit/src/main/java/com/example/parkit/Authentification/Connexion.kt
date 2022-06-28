package com.example.parkit.Authentification

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.parkit.R
import com.example.parkit.databinding.FragmentConnexionBinding
import com.example.parkit.entity.User
import com.example.parkit.retrofit.Endpoint
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*

class Connexion : Fragment() {

    private lateinit var binding: FragmentConnexionBinding
    private lateinit var  googleSingInClient : GoogleSignInClient
    private lateinit var  firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConnexionBinding.inflate(layoutInflater)
        val view = binding.root


        //confiure the google SignIn
        val googleSingInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSingInClient = GoogleSignIn.getClient(requireActivity(), googleSingInOptions)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()


        //google singIn button
        binding.signInGoogle.setOnClickListener {
           val intent = googleSingInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.progressB.visibility = View.INVISIBLE

        super.onViewCreated(view, savedInstanceState)


        requireActivity().actionBar
        binding.connect.setOnClickListener {

            val mail = binding.editMail.text
            val mdp = binding.editPassword.text

            if (mail!!.isNotBlank() && mdp!!.isNotBlank() ) {
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
            try {
                it.findNavController().navigate(R.id.action_connexionFragment_to_inscription2)
            }catch (exception :Exception){
                it.findNavController().navigate(R.id.action_connexion_to_inscription3)
            }
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
                        findNavController().popBackStack(R.id.fragmentParkings, false)

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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = accountTask.getResult(ApiException::class.java)
                if( account != null)
                    binding.progressB.visibility = View.VISIBLE
                firebaseAuthWithGoogleAccount(account)

            }catch(e:ApiException){
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun  firebaseAuthWithGoogleAccount(account: GoogleSignInAccount){
        val pref by lazy {
            requireActivity().getSharedPreferences(
                "parkitData",
                AppCompatActivity.MODE_PRIVATE
            )
        }
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth.currentUser
                var user = User(0,"","","","","","")
                val metadata = firebaseUser?.metadata
                firebaseUser!!.let {
                    user.email = it.email.toString()
                    user.nom = it.displayName.toString()
                    user.tel = it.phoneNumber.toString()

                    pref.edit() {
                        putBoolean("connected", true)
                        putString("email", user.email)
                        putString("pwd" , user.mdp)
                        putString("id", it.uid)
                        apply()
                    }
                    binding.progressB.visibility = View.INVISIBLE

                }
                if(metadata?.creationTimestamp == metadata?.lastSignInTimestamp){
                    inscription(user)
                }
                //TODO connexion avc google uid: use a special get method
                findNavController().popBackStack(R.id.fragmentParkings, false)

            }
            .addOnFailureListener{
                Toast.makeText(
                    requireActivity(),
                    "failed connexion",
                    Toast.LENGTH_SHORT
                ).show()
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
                    if (response.body()!!.toInt() == 200) {

                        pref.edit() {
                            putBoolean("connected", true)
                            putString("email", user.email.toString())
                            putString("pwd" , user.mdp.toString())

                            apply()
                        }
                        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object:
                            OnBackPressedCallback(true){
                            override fun handleOnBackPressed() {
                                findNavController().popBackStack(R.id.fragmentParkings, false)
                            }

                        })
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "response not successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}

