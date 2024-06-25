package com.example.flickrapp.data.responseData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val nsid: String,
    val username: String,
    val realname: String,
    val location: String,
    val iconserver: String,
    val iconfarm: String,
) : Parcelable {
    val url : String
        get() =  "https://farm" + "${iconfarm}.staticflickr.com/${iconserver}/buddyicons/${nsid}.jpg"
}