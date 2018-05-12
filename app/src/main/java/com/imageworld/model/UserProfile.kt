package com.imageworld.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class UserProfile(var id: Int,
                  var imageProfile: Int,
                  var username: String,
                  var bio: String) : Parcelable
