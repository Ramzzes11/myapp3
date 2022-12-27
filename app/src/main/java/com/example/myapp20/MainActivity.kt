package com.example.myapp20

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import com.example.myapp20.databinding.ActivityAuchBinding
import com.example.myapp20.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        val i = intent
        if(i!=null)bindingClass.nameSurname.text = i.getCharSequenceExtra("nameSurname")
//        bindingClass.buttonInst.setOnClickListener {
////            if(validEmail() && validPassword() || validPassword() && validEmail()){
//            val i = Intent(this, AuthActivity::class.java).apply {
//                putExtra("nameSurname", "sdf")
//            }
//            startActivity(i)
////            }
//        }
    }
}
