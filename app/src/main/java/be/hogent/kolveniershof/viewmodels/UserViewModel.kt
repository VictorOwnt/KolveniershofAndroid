package be.hogent.kolveniershof.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.base.BaseViewModel
import be.hogent.kolveniershof.model.User
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject
import javax.security.auth.login.LoginException

class UserViewModel : BaseViewModel() {

    val user = MutableLiveData<User>()
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    @Inject
    lateinit var kolvApi: KolvApi
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }

    /**
     * Registers a new user
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return user with token
     */
    fun register(firstName: String, lastName: String, email: String, password: String): User {
        try {
            return kolvApi.register(firstName, lastName, email, password)
                .doOnError { error -> onRetrieveError(error) }
                .blockingGet()
        } catch (e: Exception) {
            throw LoginException((e as HttpException).response()!!.errorBody()!!.string())
        }
    }

    /**
     * Signs in existing user
     *
     * @param email
     * @param password
     * @return user with token
     */
    fun login(email: String, password: String): User {
        try {
            return kolvApi.login(email, password)
                .doOnError { error -> onRetrieveError(error) }
                .blockingGet()

        } catch (e: Exception) {
            throw LoginException((e as HttpException).response()!!.errorBody()!!.string())
        }
    }

    /**
     * Checks if email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    fun isValidEmail(email: String): Boolean {
        return kolvApi.isValidEmail(email).blockingGet()
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}