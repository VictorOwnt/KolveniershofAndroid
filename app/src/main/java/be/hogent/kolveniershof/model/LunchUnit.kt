package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * LunchUnit entity
 *
 * @property id
 * @property lunch
 * @property mentors
 * @property clients
 */
@Parcelize
data class LunchUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "lunch")
    val lunch: String,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable