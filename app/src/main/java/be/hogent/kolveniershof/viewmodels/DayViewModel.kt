package be.hogent.kolveniershof.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import be.hogent.kolveniershof.api.KolvApi
import be.hogent.kolveniershof.base.BaseViewModel
import be.hogent.kolveniershof.model.Workday
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

import javax.inject.Inject


class DayViewModel : BaseViewModel()
{
    val workdays = MutableLiveData<List<Workday>>()
    val workday = MutableLiveData<Workday>()
    val loadingVisibility = MutableLiveData<Int>()
    val objectVisibility = MutableLiveData<Int>()


    @Inject
    lateinit var kolvApi: KolvApi
    private var disposables = CompositeDisposable()

    init {
        /*disposables.add(
            kolvApi.getWorkdays(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { results -> onRetrieveListSuccess(results) },
                    { error -> onRetrieveError(error) }
                )
        )*/
    }

    private fun onRetrieveListSuccess(results: List<Workday>) {
        workdays.value = results
        Logger.i(results.toString())
    }

    fun getWorkdayById(authToken: String, id: String) {
        disposables.add(
            kolvApi.getWorkdayById(authToken, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSingleSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
        )
    }

    fun getWorkdayByDateByUser(authToken: String, date: String, userId: String) {
        disposables.add(
            kolvApi.getWorkdayByDateByUser(authToken, date, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveSingleSuccess(result) },
                    { error -> onRetrieveError(error) }
                )
        )
    }

    fun getWorkdatByDateByUserSync(authToken: String, date: String, userId: String): Workday? {
        var workday: Workday? = null
        try {
            workday = kolvApi.getWorkdayByDateByUser(authToken, date, userId)
                .doOnError { error -> onRetrieveError(error) }
                .blockingSingle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return workday
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

    private fun onRetrieveSingleSuccess(result: Workday) {
        workday.value = result
        Logger.i(result.toString())
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
        objectVisibility.value = View.VISIBLE
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        objectVisibility.value = View.GONE
    }
}