package com.example.myapp20

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp20.databinding.ActivityAuchBinding

class AuthActivity : AppCompatActivity() {
//    TODO можна створити базову актівіті, щоб не оголошувати у кожному класі binding по новому
    lateinit var bindingClass: ActivityAuchBinding
//    TODO pref не найкраща назва, бо pref це скорочення від prefer - віддавати перевагу
    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO ActivityAuchBinding бо макет називається auch. Треба змінити
        bindingClass = ActivityAuchBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        pref = getSharedPreferences("Table",Context.MODE_PRIVATE)

        fillRegistrationFields()

        checkPasswordEmailAndMoveOnMainActivity()

        rememberPersonWhenClickOnCheckBox()

    }

    private fun fillRegistrationFields() {
//        TODO можна прислухатись до поради ide і замінити var на val
        var first = pref?.getString("email", "")
        var second = pref?.getString("password", "")

//        TODO можна скористатись with(bindingClass), щоб не повторювати кожен раз bindingClass. Детально https://kotlinlang.org/docs/scope-functions.html#with
        if(first?.length!! >5) {
//            bindingClass.tvOr?.text = first.toString()
//            TODO ide підказує, що Unnecessary safe call on a non-null receiver of type TextInputEditText.
            bindingClass.textEmail?.setText(first)
            bindingClass.textPassword?.setText(second)
        }

    }

    private fun rememberPersonWhenClickOnCheckBox() {
        bindingClass.checkBoxRememberMy.setOnClickListener{

            if(checkValidEmail()&&chackValidPassword() || chackValidPassword() && checkValidEmail()) {
//                TODO Unnecessary safe call on a non-null receiver of type TextInputEditText
                saveEmailAndPasswordInSharedPreferences(bindingClass.textEmail?.text.toString(),
                    bindingClass.textPassword?.text.toString())

            }
        }
    }


    private fun saveEmailAndPasswordInSharedPreferences(email: String, password: String) {
//        TODO var → val
        var editor = pref?.edit()
        editor?.putString("email",email)
        editor?.putString("password",password)
        editor?.apply()
    }


    private fun checkPasswordEmailAndMoveOnMainActivity() {
        bindingClass.btRegister.setOnClickListener {
            if (checkValidEmail() && chackValidPassword() || chackValidPassword() && checkValidEmail()) {
                val i = Intent(this, MainActivity::class.java).apply {
                    putExtra("nameSurname", parseEmailAndGatNameSurname())
                }
                startActivity(i)
            }
        }
    }



    fun parseEmailAndGatNameSurname():String{
//        TODO var → val
//        TODO Unnecessary non-null assertion (!!) on a non-null receiver of type TextInputEditText
        var email = bindingClass.textEmail!!.text.toString()
        var splitStr = email.split("@").toTypedArray()
        var array_string = splitStr[0].split(",",".","_",":","/","_","#","-","!",
            "0","1","2","3","4","5","6","7","8","9")
        var array_nameSurname = java.util.ArrayList<String>()
        for(i in array_string){
            if(i.length>1){
                array_nameSurname.add(i)
            }
        }
        var name = array_nameSurname.get(0)
        name = name.replace(name[0].toString(),name[0].toChar().toUpperCase().toString())
        var surname:String
//        TODO ctrl+alt+l треба нажати
        if(array_nameSurname.size==0){
            return "Name Surname"
        }else if(array_nameSurname.size<2){
            return name
        } else {
            surname = array_nameSurname.get(1)
            surname = surname.replace(surname[0].toString(),surname[0].toChar().toUpperCase().toString())
            return name+" "+surname
        }
//        TODO Unreachable code
        return "Name Surname"
    }


    private fun checkValidEmail(): Boolean {
        val pattern = Regex("^[\\w-.]+@([\\w]+\\.)+[a-z]{2,4}\$")
//        TODO Unnecessary non-null assertion (!!) on a non-null receiver of type TextInputEditText
        if (pattern.containsMatchIn(bindingClass.textEmail!!.text.toString())) {
            bindingClass.tilEmailAddress!!.helperText = "Valid email!"
            bindingClass.tilEmailAddress!!.defaultHintTextColor
            bindingClass.tilEmailAddress!!.requestFocus()
            return true
        } else {
            bindingClass.tilEmailAddress!!.error = "Invalid email!"
            bindingClass.tilEmailAddress!!.requestFocus()
            return false
        }
    }


    private fun chackValidPassword(): Boolean {
        val pattern = Regex("(?=.*[0-9])(?=.*[!@#\$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#\$%^&*]{6,}")
        if (pattern.containsMatchIn(bindingClass.textPassword!!.text.toString())) {
            bindingClass.tilEditTextPassword!!.helperText = "Valid Password!"
            bindingClass.tilEditTextPassword!!.defaultHintTextColor
            bindingClass.tilEditTextPassword!!.requestFocus()
            return true
        } else {
//            TODO Якщо підказка "Invalid email!" для email була доречна, то тут "Invalid Password!" незрозуміла. Тобто невистачає інформації, яким вимогам пароль міє відповідати
            bindingClass.tilEditTextPassword!!.error = "Invalid Password!"
            bindingClass.tilEditTextPassword!!.requestFocus()
            return false
        }
    }

}