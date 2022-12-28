package com.example.bin_searcher_app.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class DataModel(
    val number: DataNumber,
    val country: DataCountry,
    val bank: DataBank,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepared: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(DataNumber::class.java.classLoader)!!,
        parcel.readParcelable(DataCountry::class.java.classLoader)!!,
        parcel.readParcelable(DataBank::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(number, flags)
        parcel.writeParcelable(country, flags)
        parcel.writeParcelable(bank, flags)
        parcel.writeString(scheme)
        parcel.writeString(type)
        parcel.writeString(brand)
        parcel.writeByte(if (prepared) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }

        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataNumber(
    val length: Int,
    val luhn : Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeInt(length)
        parcel?.writeByte(if (luhn) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<DataNumber> {
        override fun createFromParcel(parcel: Parcel): DataNumber {
            return DataNumber(parcel)
        }

        override fun newArray(size: Int): Array<DataNumber?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataCountry(
    val numeric: String,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(numeric)
        parcel?.writeString(alpha2)
        parcel?.writeString(name)
        parcel?.writeString(emoji)
        parcel?.writeString(currency)
        parcel?.writeInt(latitude)
        parcel?.writeInt(longitude)
    }

    companion object CREATOR : Parcelable.Creator<DataCountry> {
        override fun createFromParcel(parcel: Parcel): DataCountry {
            return DataCountry(parcel)
        }

        override fun newArray(size: Int): Array<DataCountry?> {
            return arrayOfNulls(size)
        }
    }
}

data class DataBank(
    val name: String,
    val url: String,
    val phone: String,
    val city: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(phone)
        parcel.writeString(city)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataBank> {
        override fun createFromParcel(parcel: Parcel): DataBank {
            return DataBank(parcel)
        }

        override fun newArray(size: Int): Array<DataBank?> {
            return arrayOfNulls(size)
        }
    }
}
