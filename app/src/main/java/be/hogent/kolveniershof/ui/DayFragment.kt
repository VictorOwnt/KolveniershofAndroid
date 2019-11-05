package be.hogent.kolveniershof.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.google.android.material.textfield.TextInputEditText
import de.hdodenhof.circleimageview.CircleImageView
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

private const val ARG_WORKDAY_DATE = "workdayDate"
private const val ARG_WORKDAY_WEEKEND = "isWeekend"

class DayFragment : Fragment() {

    private var workdayDate : String? = null
    private var isWeekend: Boolean? = null
    private var isEmpty: Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(workdayDate: DateTime) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKDAY_DATE, workdayDate.toString("dd_MM_yyyy"))
                    putBoolean(ARG_WORKDAY_WEEKEND, (workdayDate.dayOfWeek == 6 || workdayDate.dayOfWeek == 7))
                }
            }
    }

    private lateinit var viewModel: DayViewModel
    private lateinit var sharedPrefs: SharedPreferences

    private lateinit var textDayName: TextView
    private lateinit var imageDayIcon: ImageView
    // Workday
    private lateinit var imageMorningBus: ImageView
    private lateinit var imageMorningCoffee: ImageView
    private lateinit var divider1: View
    private lateinit var imageAmActivity1: ImageView
    private lateinit var textAmActivity1: TextView
    private lateinit var imageAmMentor1: CircleImageView
    private lateinit var imageAmActivity2: ImageView
    private lateinit var textAmActivity2: TextView
    private lateinit var imageAmMentor2: CircleImageView
    private lateinit var divider2: View
    private lateinit var imageLunch: ImageView
    private lateinit var textLunch: TextView
    private lateinit var divider3: View
    private lateinit var imagePmActivity1: ImageView
    private lateinit var textPmActivity1: TextView
    private lateinit var imagePmMentor1: CircleImageView
    private lateinit var imagePmActivity2: ImageView
    private lateinit var textPmActivity2: TextView
    private lateinit var imagePmMentor2: CircleImageView
    private lateinit var divider4: View
    private lateinit var imageEveningBus: ImageView
    // Weekend
    private lateinit var inputComment: TextInputEditText
    private lateinit var buttonSendComment: Button
    // Empty / holiday
    private lateinit var textEmptyHoliday: TextView
    private lateinit var imageEmptyHoliday: ImageView

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
                    isEmpty = true
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
        // Fill view with content
        showDay(view, DateTime.parse(workdayDate, DateTimeFormat.forPattern("dd_MM_yyyy").withLocale(Locale.getDefault())))
        if (isEmpty) showEmptyDay(view, false)
        viewModel.workday.observe(this, Observer { workday ->
            when {
                isWeekend!! -> showWeekend(view, workday.comments[0].toString()) // todo
                workday.isHoliday!! -> showEmptyDay(view, true)
                else -> {
                    showBus(view, workday.morningBusses[0], true)
                    showActivity(view, workday.amActivities.toTypedArray(), true)
                    showLunch(view, workday.lunch)
                    showActivity(view, workday.pmActivities.toTypedArray(), false)
                    showBus(view, workday.eveningBusses[0], false)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.menu_calendar)
    }

    @SuppressLint("DefaultLocale")
    private fun showDay(view: View, date: DateTime) {
        textDayName = view.findViewById(R.id.textDayName)
        imageDayIcon = view.findViewById(R.id.imageDayIcon)
        val icons = arrayOf(
            R.drawable.ic_day_moon,
            R.drawable.ic_day_beach_ball,
            R.drawable.ic_day_angry,
            R.drawable.ic_day_cloud_lightning,
            R.drawable.ic_day_bird,
            R.drawable.ic_day_flower_bouquet,
            R.drawable.ic_day_sun
        )
        imageDayIcon.setImageResource(icons[date.dayOfWeek-1])
        textDayName.text = date.toString("EEEE").capitalize()
    }

    private fun showEmptyDay(view: View, isHoliday: Boolean) {
        textEmptyHoliday = view.findViewById(R.id.textEmptyHoliday)
        imageEmptyHoliday = view.findViewById(R.id.imageEmptyHoliday)
        if (isHoliday) {
            imageEmptyHoliday.setImageResource(R.drawable.ic_beach_umbrella)
            textEmptyHoliday.text = getString(R.string.holiday)
        } else {
            imageEmptyHoliday.setImageResource(R.drawable.ic_ask_question)
            textEmptyHoliday.text = getString(R.string.empty_day)
        }
    }

    private fun showWeekend(view: View, comment: String?) {
        inputComment = view.findViewById(R.id.input_comment)
        buttonSendComment = view.findViewById(R.id.buttonSendComment)
        // Fill comment if present
        if (!comment.isNullOrBlank()) {
            inputComment.text = Editable.Factory.getInstance().newEditable(comment)
        }
    }

    private fun showBus(view: View, busUnit: BusUnit?, isMorning: Boolean) {
        if (isMorning) {
            imageMorningBus = view.findViewById(R.id.imageMorningBus)
            imageMorningCoffee = view.findViewById(R.id.imageMorningCoffee)
            divider4 = view.findViewById(R.id.divider4)
            if (busUnit != null) {
                imageMorningBus.setImageResource(R.drawable.ic_menu_bus)
                imageMorningBus.setColorFilter(Color.parseColor(busUnit.bus.color))
            } else {
                imageMorningBus.visibility = View.GONE
            }
            imageMorningCoffee.setImageResource(R.drawable.ic_coffee)
        } else {
            imageEveningBus = view.findViewById(R.id.imageEveningBus)
            if (busUnit != null) {
                imageEveningBus.setImageResource(R.drawable.ic_menu_bus)
                imageEveningBus.setColorFilter(Color.parseColor(busUnit.bus.color))
            } else {
                imageEveningBus.visibility = View.GONE
                divider4.visibility = View.GONE
            }
        }
    }

    private fun showActivity(view: View, activityUnits: Array<ActivityUnit?>, isAm: Boolean) {
    }

    private fun showLunch(view: View, lunchUnit: LunchUnit?) {
    }

}
