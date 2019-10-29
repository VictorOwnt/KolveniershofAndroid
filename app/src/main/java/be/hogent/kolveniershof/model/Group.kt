package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Group entity
 *
 * @property id
 * @property name
 * @property members
 */
@Parcelize
data class Group(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "name")
    val name: String,
    @field:Json(name = "members")
    val members: MutableList<User>
) : Parcelable {
    override fun toString(): String {
        return name
    }
}