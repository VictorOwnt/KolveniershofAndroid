package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * User entity
 *
 * @property id
 * @property firstName
 * @property lastName
 * @property email
 * @property isAdmin
 * @property birthday
 * @property absentDates
 * @property imgUrl
 * @property token
 */
@Parcelize
data class User(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "firstName")
    val firstName: String,
    @field:Json(name = "lastName")
    val lastName: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "admin")
    val isAdmin: Boolean = false,
    @field:Json(name = "birthday")
    val birthday: Date,
    //@field:Json(name = group)
    //val group: Group,
    @field:Json(name = "absentDates")
    val absentDates: MutableList<Date> = mutableListOf(),
    @field:Json(name = "picture")
    val imgUrl: String? = null,
    @field:Json(name = "tempToken")
    val token: String? = null
) : Parcelable