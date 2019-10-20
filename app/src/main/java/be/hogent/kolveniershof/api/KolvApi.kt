package be.hogent.kolveniershof.api

import be.hogent.kolveniershof.model.User
import be.hogent.kolveniershof.model.Workday
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface KolvApi {

    /**
     * Registers a new user
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("admin") isAdmin: Boolean
    ): Single<User>

    /**
     * Signs in existing users
     *
     * @param userName
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("userName") userName: String,
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
     * Gets user by userName
     *
     * @param userName
     * @return user
     */
    @GET("users/{userName}")
    fun getUserByEmail(@Path("userName") userName: String): Observable<User>

    /**
     * Gets user by id
     *
     * @param id
     * @return user
     */
    @GET("users/id/{id}")
    fun getUserById(@Path("id") id: String): Observable<User>

    /**
     * Gets workdays
     *
     * @return workdays
     */
    @GET("workdays")
    fun getWorkdays() : Observable<List<Workday>>

    /**
     * Gets workday by id
     *
     * @param id
     * @return workday
     */
    @GET("workdays/{id}")
    fun getWorkdayById(@Path("id") id: String) : Observable<Workday>


}