package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Bus entity
 *
 * @property id
 * @property name
 * @property color
 * @property iconUrl
 */
@Parcelize
data class Bus(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "color")
    val color: String
) : Parcelable {
    override fun toString(): String {
        return name.trim().capitalize()
    }
}