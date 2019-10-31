package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import be.hogent.kolveniershof.databinding.FragmentEmptyHolidayBinding
import be.hogent.kolveniershof.databinding.FragmentWeekendBinding
import be.hogent.kolveniershof.model.ActivityUnit
import be.hogent.kolveniershof.model.BusUnit
import be.hogent.kolveniershof.model.LunchUnit
import be.hogent.kolveniershof.model.Workday
import be.hogent.kolveniershof.viewmodels.DayViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.joda.time.DateTime

private const val ARG_WORKDAY_DATE = "workdayDate"
private const val ARG_WORKDAY_WEEKEND = "isWeekend"

class DayFragment : Fragment() {

    private var workdayDate : String? = null
    private var isWeekend: Boolean? = null

    companion object {
        @JvmStatic
        fun newInstance(workdayDate: DateTime) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKDAY_DATE, workdayDate.toString("dd_MM_yyyy"))
                    putBoolean(ARG_WORKDAY_WEEKEND, (workdayDate.toString("e") == "6" || workdayDate.toString("e") == "7"))
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

        // Get shared preferences
        sharedPrefs = activity!!.getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        // Instantiate viewModel
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)

        // Get workday
        arguments?.getString("workdayDate")?.let { viewModel.getWorkdayByDateByUser(sharedPrefs.getString("TOKEN", "")!!, it, sharedPrefs.getString("ID", "")!!) }
        viewModel.workday.removeObservers(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root: View?
        val workday: Workday? = viewModel.getWorkdayByDateByUserSync(sharedPrefs.getString("TOKEN", "")!!, arguments?.getString("workdayDate")!!, sharedPrefs.getString("ID", "")!!)
        when {
                workday == null -> {
                    val binding: FragmentEmptyHolidayBinding =
                        DataBindingUtil.inflate(inflater, R.layout.fragment_empty_holiday, container, false)
                    binding.viewmodel = viewModel
                    binding.lifecycleOwner = this.viewLifecycleOwner
                    root = binding.root
                }
                isWeekend!! -> {
                    val binding: FragmentWeekendBinding =
                        DataBindingUtil.inflate(inflater, R.layout.fragment_weekend, container, false)
                    binding.viewmodel = viewModel
                    binding.lifecycleOwner = this.viewLifecycleOwner
                    root = binding.root
                }
                workday.isHoliday!! -> {
                    val binding: FragmentEmptyHolidayBinding =
                        DataBindingUtil.inflate(inflater, R.layout.fragment_empty_holiday, container, false)
                    binding.viewmodel = viewModel
                    binding.lifecycleOwner = this.viewLifecycleOwner
                    root = binding.root
                }
                else -> {
                    val binding: FragmentDayBinding =
                        DataBindingUtil.inflate(inflater, R.layout.fragment_day, container, false)
                    binding.viewmodel = viewModel
                    binding.lifecycleOwner = this.viewLifecycleOwner
                    root = binding.root
                }
            }
        return root
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
        (activity as AppCompatActivity).supportActionBar?.title = "Agenda placeholder"
    }


    }


}
