package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.databinding.FragmentDayBinding
import be.hogent.kolveniershof.model.ActivityUnit
import be.hogent.kolveniershof.model.BusUnit
import be.hogent.kolveniershof.model.LunchUnit
import be.hogent.kolveniershof.viewmodels.DayViewModel
import org.joda.time.DateTime
import org.w3c.dom.Text

private const val ARG_WORKDAY_DATE = "workdayDate"
private const val ARG_WORKDAY_WEEKEND = "isWeekend"

class DayFragment : Fragment() {

    private var workdayDate : String? = null
    private var isWeekend: Boolean? = null

    companion object {
        @JvmStatic
        fun newInstance(workdayDate: DateTime?) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    var date: String? = null
                    var weekend = false
                    if (workdayDate != null) {
                        date = workdayDate.toString("dd_MM_yyyy")
                        if (workdayDate.toString("e") == "6" || workdayDate.toString("e") == "7")
                            weekend = true
                    }
                    putString(ARG_WORKDAY_DATE, date)
                    putBoolean(ARG_WORKDAY_WEEKEND, weekend)
                }
            }
    }

    private lateinit var viewModel: DayViewModel
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var imageMorningBus: ImageView
    private lateinit var imageMorningCoffee: ImageView
    private lateinit var divider1: View
    private lateinit var imageAmActivity1: ImageView
    private lateinit var textAmActivity1: TextView
    // TODO mentors
    private lateinit var imageAmActivity2: ImageView
    private lateinit var textAmActivity2: TextView
    // TODO mentors
    private lateinit var divider2: View
    private lateinit var imageLunch: ImageView
    private lateinit var textLunch: TextView
    private lateinit var divider3: View
    private lateinit var imagePmActivity1: ImageView
    private lateinit var textPmActivity1: TextView
    // TODO mentors
    private lateinit var imagePmActivity2: ImageView
    private lateinit var textPmActivity2: TextView
    // TODO mentors
    private lateinit var divider4: View
    private lateinit var imageEveningBus: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workdayDate = it.getString(ARG_WORKDAY_DATE)
            isWeekend = it.getBoolean(ARG_WORKDAY_WEEKEND)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)

        // Inflate the layout for this fragment
        val binding: FragmentDayBinding = if (isWeekend!!)
            DataBindingUtil.inflate(inflater, R.layout.fragment_weekend, container, false)
        else
            DataBindingUtil.inflate(inflater, R.layout.fragment_day, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Instantiate view objects
        imageMorningBus = view.findViewById(R.id.imageMorningBus)
        imageMorningCoffee = view.findViewById(R.id.imageMorningCoffee)
        imageAmActivity1 = view.findViewById(R.id.imageAmActivity1)
        textAmActivity1 = view.findViewById(R.id.textAmActivity1)
        // TODO mentors
        imageAmActivity2 = view.findViewById(R.id.imageAmActivity2)
        textAmActivity2 = view.findViewById(R.id.textAmActivity2)
        // TODO mentors
        imageLunch = view.findViewById(R.id.imageLunch)
        textLunch = view.findViewById(R.id.textLunch)
        imagePmActivity1 = view.findViewById(R.id.imagePmActivity1)
        textPmActivity1 = view.findViewById(R.id.textPmActivity1)
        // TODO mentors
        imagePmActivity2 = view.findViewById(R.id.imagePmActivity2)
        textPmActivity2 = view.findViewById(R.id.textPmActivity2)
        // TODO mentors
        imageEveningBus = view.findViewById(R.id.imageEveningBus)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.dag_naam_test)
        sharedPrefs = activity!!.getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        arguments?.getString("workdayDate")?.let { viewModel.getWorkdayByDateByUser(sharedPrefs.getString("TOKEN", "")!!, it, sharedPrefs.getString("ID", "")!!) }
        viewModel.workday.removeObservers(this)
        viewModel.workday.observe(this, Observer { workday ->
            // TODO hier code voor verbegen elementen en andere dergelijke zaken
        })

    }


}
