package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * BusUnit entity
 *
 * @property id
 * @property bus
 * @property mentors
 * @property clients
 */
@Parcelize
data class BusUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "bus")
    val bus: Bus,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable