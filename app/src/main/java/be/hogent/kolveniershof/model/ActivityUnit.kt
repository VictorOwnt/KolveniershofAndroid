package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * ActivityUnit entity
 *
 * @property id
 * @property activity
 * @property mentors
 * @property clients
 */
@Parcelize
data class ActivityUnit(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name  = "activity")
    val activity: Activity,
    @field:Json(name = "mentors")
    val mentors: MutableList<User>,
    @field:Json(name = "clients")
    val clients: MutableList<User>
) : Parcelable