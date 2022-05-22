package com.example.a17_programacion

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {
    //Se crean las variables lateinit
    lateinit var email: EditText
    lateinit var Paso: EditText
    private lateinit var corn: EditText
    private lateinit var continuar: ImageButton
    lateinit var before: ImageButton

    // Se crea el objeto de autentiacion de la base firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //Se buscan las variables por sus ID
        email = findViewById(R.id.paso)
        Paso = findViewById(R.id.contra)
        corn = findViewById(R.id.confirma)
        before = findViewById(R.id.guardar)
        continuar = findViewById(R.id.after)

        // Se inicializa la autenticacion de la firebase
        auth = Firebase.auth

        before.setOnClickListener {
            register()
        }

        continuar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
    private fun register(){
        val emaile=email.text.toString()
        val contrase=Paso.text.toString()
        val confirmacion=corn.text.toString()
        if(emaile.isBlank()||contrase.isBlank()||confirmacion.isBlank()){
            //homerochino
            Toast.makeText(this, "por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if(contrase!=confirmacion){
            Toast.makeText(this, "confirma que las contraseñas sean iguales", Toast.LENGTH_SHORT)
                .show()
            return
        }
        //Se usa la variable auth., para crear usuario con correo y contraseña, trayendo los parametros
        auth.createUserWithEmailAndPassword(emaile, contrase)
            .addOnCompleteListener(this) { task ->
                //Si se hizo correctamente el registro, se manda un toast diciendo que se ha registrado correctamente
                //y tambien pasa al activity de login y se finaliza este
                if (task.isSuccessful) {
                    //Inicio de sesión exitoso, actualización de la interfaz de usuario con la información del usuario registrado
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "¡Usuario Registrado Correctamente!",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //Si hubo fallos a la hora de crearlo, se envia un Toast diciendo que hubo fallo de autentificacion
                    //y se vacian los edittext
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Fallo de Autenticacion",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}