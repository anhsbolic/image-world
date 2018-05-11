package com.imageworld.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Post(var id: Int,
                var imageProfile: Int,
                var username: String,
                var imagePost: Int,
                var seenBy: String,
                var post: String,
                var totalComment: Int) : Parcelable