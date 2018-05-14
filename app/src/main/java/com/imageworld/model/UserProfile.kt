package com.imageworld.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class UserProfile(var id: String?,
                  var imageProfile: String?,
                  var firstName: String?,
                  var lastName: String?,
                  var username: String?,
                  var bio: String?) : Parcelable
