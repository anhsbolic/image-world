package com.imageworld.model

import android.graphics.Bitmap
import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class UserProfile(var id: String?,
                  var imageProfile: Bitmap?,
                  var firstName: String?,
                  var lastName: String?,
                  var username: String?,
                  var bio: String?) : Parcelable
