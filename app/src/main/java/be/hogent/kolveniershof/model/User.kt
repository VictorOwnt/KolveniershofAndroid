package be.hogent.kolveniershof.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * User entity
 *
 * @property id
 * @property firstName
 * @property lastName
 * @property email
 * @property token (only for logged in user)
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
    // TODO - all fields
    @field:Json(name = "tempToken")
    val token: String? = null
) : Parcelable