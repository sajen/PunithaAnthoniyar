package com.church.punithaanthoniyar.utils

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseUtil {
    companion object FirebaseInstanceObj{

        fun getFirebaseFirestore() : FirebaseFirestore{
            return FirebaseFirestore.getInstance(getFirebaseApp())
        }

        fun getFirebaseDatabase() : FirebaseDatabase {
            return FirebaseDatabase.getInstance(getFirebaseApp())
        }

        fun getFirebaseApp() : FirebaseApp{
            return FirebaseApp.getInstance()
        }

        fun getFirebaseAuth() : FirebaseAuth{
            return FirebaseAuth.getInstance(getFirebaseApp())
        }

        fun getFirebaseStorage() : FirebaseStorage {
            return FirebaseStorage.getInstance(getFirebaseApp())
        }
    }
}