package com.church.punithaanthoniyar.home.presenter

import com.church.punithaanthoniyar.home.IHomeContract
import com.church.punithaanthoniyar.home.model.Home
import com.church.punithaanthoniyar.utils.FirebaseUtil
import com.google.firebase.firestore.DocumentSnapshot
import tbm.church.AppConstants.Companion.FIRESTORE_BASE_PATH
import tbm.church.AppConstants.Companion.FIRESTORE_HOME
import com.google.firebase.storage.StorageReference
import tbm.church.AppConstants.Companion.FIREBASE_HOME_URL


class HomeFragmentPresenter : IHomeContract.IHomePresenterContract {

    lateinit var view : IHomeContract.IHomeViewContract

    override fun setScreenView (view : IHomeContract.IHomeViewContract){
        this.view = view
    }

    override fun getImageListFromFirestore() {

        FirebaseUtil.FirebaseInstanceObj.getFirebaseFirestore()
            .collection(FIRESTORE_BASE_PATH)
            .document(FIRESTORE_HOME).get().addOnCompleteListener { resultVal ->
                if (resultVal.isSuccessful && resultVal.result != null){
                    val document :DocumentSnapshot = resultVal.result!!

                    val homeModel : Home = document.toObject((Home::class.java))!!

                    if (homeModel.images.isNotEmpty()) {

                        val imageUrlList = arrayListOf<String>()
                        var i = 0
                        for (url in homeModel.images){

                            FirebaseUtil.getFirebaseStorage().reference.child(FIREBASE_HOME_URL).child(url).downloadUrl.addOnSuccessListener {

                                i++

                                imageUrlList.add(it.toString())

                                if (i == homeModel.images.size){
                                    if (imageUrlList.isNotEmpty())
                                        view.updateImage(imageUrlList)
                                }

                            }
                        }
                    }
                }
            }
    }


}