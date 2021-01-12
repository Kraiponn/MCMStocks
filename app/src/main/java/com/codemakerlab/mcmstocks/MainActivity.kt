package com.codemakerlab.mcmstocks

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codemakerlab.mcmstocks.databinding.ActivityMainBinding
import com.pixplicity.easyprefs.library.Prefs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        if(Prefs.getBoolean(PREF_KEY_IS_AUTH, false)) {
            openHomePage()
        }else{
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            initInstance()
        }
    }

    private fun initInstance() {
        binding.loginScrollView.apply {
            isHorizontalScrollBarEnabled = false
            isVerticalScrollBarEnabled = false
        }

        binding.loginBtnSubmit.setOnClickListener {
            validateValues()
        }
    }

    private fun validateValues() {
        val email = binding.loginEdtEmail.text.toString()
        val password = binding.loginEdtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Invalid validation form values")
            return
        }

        if(email == "admin@gmail.com" && password == "admin") {
            Prefs.putBoolean(PREF_KEY_IS_AUTH, true)
            Prefs.putString(PREF_KEY_EMAIL_LOGIN, email)
            openHomePage()
        } else {
            showToast("Email or password is incorrect")
        }
    }

    private fun openHomePage() {
        Intent(this@MainActivity, HomePageActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }
}