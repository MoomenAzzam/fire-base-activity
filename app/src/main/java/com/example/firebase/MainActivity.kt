package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = Firebase.database
        val myRef = database.getReference("Person")

        btn_send.setOnClickListener {
            var name = name_txt.text.toString()
            val email = email_txt.text.toString()
            val password = password_txt.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val person = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password,
                )
                myRef.child("person").child("$count").setValue(person)
                count++
                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                name_txt.setText("")
                email_txt.setText("")
                password_txt.setText("")
            }
        }
        btn_getdata.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val value = snapshot.getValue().toString()
                    data_tv.setText(value)
                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()

                }
            })
        }

    }
}