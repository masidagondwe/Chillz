package com.tech.temwa.chillz

import com.tech.temwa.chillz.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener
import com.firebase.ui.auth.AuthUI
import java.util.*


class FirebaseUtil() {

    var mFirebaseDatabase= FirebaseDatabase.getInstance()
    var mDatabaseReference: DatabaseReference? = null
    private var firebaseUtil: FirebaseUtil? = null
    var mFirebaseAuth= FirebaseAuth.getInstance()
    var mStorage= FirebaseStorage.getInstance()
    var mAuthListener = FirebaseAuth.AuthStateListener {  }
    private val RC_SIGN_IN = 123
    var caller: HomeActivity? = null
    var isAdmin: Boolean = false

    fun openFbReference(ref: String, callerActivity: HomeActivity) {
        if (firebaseUtil == null) {
            firebaseUtil = FirebaseUtil()
            mFirebaseDatabase = FirebaseDatabase.getInstance()
            mFirebaseAuth = FirebaseAuth.getInstance()
            caller = callerActivity

            mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
                if (firebaseAuth.currentUser == null) {
                    signIn()
                } else {
                    var userID = firebaseAuth.uid
                    checkAdmin(userID.toString())
                }
                Toast.makeText(callerActivity.baseContext, "Welcome back!", Toast.LENGTH_LONG)
                    .show()
            }
            connectStorage()

        }

        //mDeals = ArrayList<TravelDeal>()
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref)
    }


    private fun signIn() {
        // Choose authentication providers
        val providers = Arrays.asList(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        caller!!.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun checkAdmin(uid: String) {
        isAdmin = false
        val ref = mFirebaseDatabase.getReference().child("administrators")
            .child(uid)
        val listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                isAdmin = true
                //caller.showMenu()
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        ref.addChildEventListener(listener)
    }

    fun attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthListener)
    }

    fun detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener)
    }

    fun connectStorage() {
        mStorage = FirebaseStorage.getInstance()
       // var mStorageRef = mStorage.getReference().child("deals_pictures")
    }
}