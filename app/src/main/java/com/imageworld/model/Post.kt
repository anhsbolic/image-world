package com.imageworld.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Post(var id: String?,
                var imageProfile: String?,
                var username: String?,
                var imagePost: String?,
                var capture: String?,
                var listComments: List<String>?) : Parcelable