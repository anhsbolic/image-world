package com.imageworld.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PostComment(var id: String?,
                       var postId: String?,
                       var urlImgProfile: String?,
                       var username: String?,
                       var content: String?) : Parcelable