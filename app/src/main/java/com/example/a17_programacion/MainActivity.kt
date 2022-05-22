package com.example.a17_programacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    //se crean las variables  de los componentes del  activity main.xml
    private lateinit var tarde: ImageButton
    lateinit var correos: EditText
    private lateinit var contrasena: EditText
    lateinit var nexto: ImageButton
    //creamos la firebase con su autentificacion
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //llamamos los objetos por su id del xml
        tarde = findViewById(R.id.before)
        nexto = findViewById(R.id.next)
        correos = findViewById(R.id.correo)
        contrasena = findViewById(R.id.password)
        //Se hace una instancia de la aunteticacion
        auth = FirebaseAuth.getInstance()

        //creamos un evento clic para iniciar la funcion "inicio"
        nexto.setOnClickListener {
            inicio()
        }
        //creamos un evento click para trnsladarnos al activity de registro
        tarde.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun inicio(){
        //vamos a definir las variables
        val email=correos.text.toString()
        val contras=contrasena.text.toString()
        //La variable con el tipo de inicio de sesion, con sus parametros
        auth.signInWithEmailAndPassword(email,contras).addOnCompleteListener(this){
            //Si el proceso fue exitoso, marcar un Toast diciendo que el acceso fue autorizado
            if(it.isSuccessful){
                Toast.makeText(this, "Acceso Autorizado!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, imagen::class.java)
                startActivity(intent)
                finish()
            }else
            //De lo contrario, se mandara un Toast que el acceso no fue autorizado y se vacian los editext
                Toast.makeText(this, "Acceso no autorizado! ", Toast.LENGTH_SHORT).show()
        }

    }


}