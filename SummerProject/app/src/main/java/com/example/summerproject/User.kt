package com.example.summerproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//21.08.16 eemdeeks : RDB에서 user정보 가저오는 adapter
@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String): Parcelable{
    constructor() : this("","","")
}