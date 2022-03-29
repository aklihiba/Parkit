package com.example.projet_mobile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText

import android.widget.ImageButton
import android.widget.ImageView


class authentification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        // declaration de l'animation

        val submit = findViewById(R.id.imageButton3) as ImageButton
        val email = findViewById<android.widget.EditText>(R.id.editTextTextEmailAddress) as EditText
        val pwd =findViewById<android.widget.EditText>(R.id.editTextTextPassword) as EditText
        val validat_icon = findViewById<android.widget.ImageView>(R.id.imageView7) as ImageView
        val anim = AnimationUtils.loadAnimation(this, R.anim.animation)

        submit.setOnClickListener {

            it.setBackgroundResource(R.drawable.roundall_blue)
            it.startAnimation(anim)
           email.startAnimation(anim)
            pwd.startAnimation(anim)
            validat_icon.startAnimation(anim)
            anim.setFillAfter(false)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}
