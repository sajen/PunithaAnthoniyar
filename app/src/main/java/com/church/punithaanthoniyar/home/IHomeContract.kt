package com.church.punithaanthoniyar.home

import com.google.firebase.storage.StorageReference

interface IHomeContract {

    interface IHomeViewContract{
        fun updateImage(imageList : ArrayList<String>)
    }

    interface IHomePresenterContract{
        fun setScreenView (view : IHomeContract.IHomeViewContract)
        fun getImageListFromFirestore()
    }
}