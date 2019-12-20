package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Workday entity
 *
 * @property id
 * @property date
 * @property daycareMentors
 * @property morningBusses
 * @property amActivities
 * @property lunch
 * @property pmActivities
 * @property eveningBusses
 * @property isHoliday
 */
@Parcelize
data class Workday(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "date")
    val date: Date,
    @field:Json(name = "daycareMentors")
    val daycareMentors: MutableList<User>,
    @field:Json(name = "morningBusses")
    val morningBusses: MutableList<BusUnit>,
    @field:Json(name  = "amActivities")
    val amActivities: MutableList<ActivityUnit>,
    @field:Json(name = "lunch")
    val lunch: LunchUnit,
    @field:Json(name = "pmActivities")
    val pmActivities: MutableList<ActivityUnit>,
    @field:Json(name = "eveningBusses")
    val eveningBusses: MutableList<BusUnit>,
    @field:Json(name = "holiday")
    val isHoliday: Boolean? = false,
    @field:Json(name = "comments")
    val comments: MutableList<Comment>
) : Parcelable

@Parcelize
data class Comment(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "comment")
    var comment: String,
    @field:Json(name = "user")
    val user: User
) : Parcelable {
    override fun toString(): String {
        return comment.trim()
    }
}