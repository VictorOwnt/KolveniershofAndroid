package be.hogent.kolveniershof.api

import be.hogent.kolveniershof.model.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface KolvApi {

    /**
     * Registers a new user
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("admin") isAdmin: Boolean
    ): Single<User>

    /**
     * Signs in existing users
     *
     * @param email
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<User>

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    fun isValidEmail(@Field("email") email: String): Single<Boolean>

    /**
     * Gets user by email
     *
     * @param email
     * @return user
     */
    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Observable<User>

    /**
     * Gets user by id
     *
     * @param id
     * @return
     */
    @GET("users/id/{id}")
    fun getUserById(@Path("id") id: String): Observable<User>

}