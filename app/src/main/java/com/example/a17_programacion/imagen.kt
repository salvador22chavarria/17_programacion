package com.example.a17_programacion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class imagen : AppCompatActivity() {
    //se crean la variables prrivadas y las que se incian despues


    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var vista:ImageView
    lateinit var subir:ImageButton
    lateinit var  escoger: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagen)
        //se crea la variable y se manda a llamar por el id del xml
        vista=findViewById(R.id.mirame)
        subir=findViewById(R.id.on)
        escoger=findViewById(R.id.between)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        //creamos el evento clic para llamar las funciones
        subir.setOnClickListener {
            cargar()
        }
        escoger.setOnClickListener {
            seleccionar()
        }


    }
    //se crea la funcion privada para crear una intencion para seleccionar la imagen
    private fun seleccionar(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "seleccciona una imagen"), PICK_IMAGE_REQUEST)
    }
    //se crea una condicion para devolver la informacion de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                vista.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun cargar(){
        //se crea una condicion para validar si eligio un archivo y si esto es verdadero subirlo a la firebase si esto es falso mandara un toast pidiendo seleccionar una imagen
        if(filePath != null){
            val ref = storageReference?.child("imagenes listas/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)
            Toast.makeText(this, "Imagen subida", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Selecciona una imagen", Toast.LENGTH_SHORT).show()
    }
    }
}