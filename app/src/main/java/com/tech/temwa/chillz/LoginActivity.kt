package com.tech.temwa.chillz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    //widgets
    //private var btnLogin = findViewById<Button>(R.id.btn_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun btnOnClick(view: View) {
        // Need to use LoginActivity.this to access context because we're inside an interface
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish() //make sure to finish the activity so it's removed from the activity stack
    }
}
