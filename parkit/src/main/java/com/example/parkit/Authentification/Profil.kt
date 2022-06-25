package com.example.parkit.Authentification

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.parkit.R
import com.example.parkit.databinding.FragmentProfilBinding
import com.example.parkit.entity.User
import com.example.parkit.retrofit.Endpoint
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import java.io.File


class Profil : Fragment() {

    private lateinit var binding: FragmentProfilBinding
    private lateinit var profil:User
    private lateinit var imageUri : Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(layoutInflater)
        val view = binding.root
        val pref by lazy { requireActivity().getSharedPreferences("parkitData", AppCompatActivity.MODE_PRIVATE) }

        val con = pref.getBoolean("connected", false)
        if (!con)  {
            findNavController().navigate(R.id.action_fragmentProfil_to_connexion)

        }
        binding.editName.visibility = INVISIBLE
        binding.editPrenom.visibility = INVISIBLE
        binding.editNumero.visibility = INVISIBLE
        binding.editPwd.visibility = INVISIBLE
        binding.progressB.visibility = INVISIBLE
        binding.modifier.visibility = INVISIBLE
        binding.annuler.visibility = INVISIBLE

        this.context?.let { getProfil(it) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editNAME.setOnClickListener {
            binding.nom.visibility = INVISIBLE
            binding.editName.visibility = VISIBLE
            binding.modifier.visibility = VISIBLE
            binding.annuler.visibility = VISIBLE
        }

        binding.editPRENOM.setOnClickListener {
            binding.prenom.visibility = INVISIBLE
            binding.editPrenom.visibility = VISIBLE
            binding.modifier.visibility = VISIBLE
            binding.annuler.visibility = VISIBLE
        }

        binding.editNUMPHONE.setOnClickListener {
            binding.telephone.visibility = INVISIBLE
            binding.editNumero.visibility = VISIBLE
            binding.modifier.visibility = VISIBLE
            binding.annuler.visibility = VISIBLE
        }

        binding.editPWD.setOnClickListener {
            binding.pwd.visibility = INVISIBLE
            binding.editPwd.visibility = VISIBLE
            binding.modifier.visibility = VISIBLE
            binding.annuler.visibility = VISIBLE
        }

        binding.editPicture.setOnClickListener{
            binding.modifier.visibility = VISIBLE
            binding.annuler.visibility = VISIBLE

            //selecting the picture
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)

            startActivityForResult(intent,100)

        }

        binding.annuler.setOnClickListener{
            annuler()
        }

        binding.modifier.setOnClickListener{
            var newprofil = profil.copy()
            if (binding.editName.visibility == VISIBLE && binding.editName.text!!.isNotBlank()){
                newprofil.nom = binding.editName.text.toString()
            }
            if (binding.editPrenom.visibility == VISIBLE && binding.editPrenom.text!!.isNotBlank()){
                newprofil.prenom = binding.editPrenom.text.toString()
            }
            if (binding.editNumero.visibility == VISIBLE && binding.editNumero.text!!.isNotBlank()){
                newprofil.tel = binding.editNumero.text.toString()
            }
            if (binding.editPwd.visibility == VISIBLE && binding.editPwd.text!!.isNotBlank()){
                newprofil.mdp = binding.editPwd.text.toString()
            }
            if(imageUri != null){
                uploadImage(newprofil)
            }else{
                updateProfil(newprofil)
            }

            annuler()
        }
    }

    fun annuler(){
        binding.editName.visibility = INVISIBLE
        binding.editPrenom.visibility = INVISIBLE
        binding.editNumero.visibility = INVISIBLE
        binding.editPwd.visibility = INVISIBLE
        binding.progressB.visibility = INVISIBLE
        binding.modifier.visibility = INVISIBLE
        binding.annuler.visibility = INVISIBLE

        binding.nom.visibility = VISIBLE
        binding.prenom.visibility = VISIBLE
        binding.telephone.visibility = VISIBLE
        binding.pwd.visibility = VISIBLE
    }

    fun getProfil(context: Context){

        val pref by lazy {
            requireActivity().getSharedPreferences(
                "parkitData",
                AppCompatActivity.MODE_PRIVATE
            )
        }
        val id = pref.getString("id","0")?.toInt()!!
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progressB.visibility = INVISIBLE
                Toast.makeText(context, "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.progressB.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getUser(id)
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    profil = response.body()!!
                    binding.nom.text = profil.nom
                    binding.email.text = profil.email
                    binding.telephone.text = profil.tel
                    binding.prenom.text = profil.prenom

                    if (profil.photo != ""){
                        val ref = FirebaseStorage.getInstance().getReference(profil.photo)
                        val localfile = File.createTempFile("profilImpage","jpg")
                        ref.getFile(localfile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                            binding.profilImage.setImageBitmap(bitmap)
                        }.addOnFailureListener {
                            Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                                .show()
                        }
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

    fun updateProfil( profil:User){

        val pref by lazy {
            requireActivity().getSharedPreferences(
                "parkitData",
                AppCompatActivity.MODE_PRIVATE
            )
        }
        val id = pref.getString("id","0")?.toInt()!!
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                binding.progressB.visibility = INVISIBLE
                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.progressB.visibility = VISIBLE
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = Endpoint.createEndpoint().updateProfil(id,profil)
            withContext(Dispatchers.Main) {
                binding.progressB.visibility = INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    binding.nom.text = profil.nom
                    binding.email.text = profil.email
                    binding.telephone.text = profil.tel
                    binding.prenom.text = profil.prenom
                    if (profil.photo != ""){
                        val ref = FirebaseStorage.getInstance().getReference(profil.photo)
                        val localfile = File.createTempFile("profilImpage","jpg")
                        ref.getFile(localfile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                            binding.profilImage.setImageBitmap(bitmap)
                        }.addOnFailureListener {
                            Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else{
                        binding.profilImage.setImageDrawable(getResources().getDrawable(R.drawable.parking4))
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            binding.profilImage.setImageURI(imageUri)
        }
    }

    fun uploadImage(profil: User){

        val filename = profil.email
        profil.photo = "users/$filename.jpg"
        val storageReference = FirebaseStorage.getInstance().getReference(profil.photo)
        storageReference.putFile(imageUri).
                addOnSuccessListener {
                    updateProfil(profil)

                }.addOnFailureListener{

        }

    }

}