package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.databinding.FragmentDateSelectorBinding
import be.hogent.kolveniershof.viewmodels.DayViewModel
import kotlinx.android.synthetic.main.content_main.*
import org.joda.time.DateTime

// Number of pages in ViewPager (8 weeks total)
private const val NUM_PAGES = 56
private const val ARG_WORKDAY_DATE = "workdayDate"

class DateSelectorFragment : Fragment() {

    private var workdayDate: DateTime? = null

    companion object {
        @JvmStatic
        fun newInstance(date: DateTime) =
            DateSelectorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKDAY_DATE, date.toString())
                }
            }
    }

    private lateinit var viewModel: DayViewModel
    private lateinit var sharedPrefs: SharedPreferences
    // Phone mode
    private lateinit var mPager: ViewPager
    private lateinit var dateSelectorMinusTwo: DateButton
    private lateinit var dateSelectorMinusOne: DateButton
    private lateinit var dateSelectorNow: DateButton
    private lateinit var dateSelectorPlusOne: DateButton
    private lateinit var dateSelectorPlusTwo: DateButton
    // Tablet mode
    private lateinit var previousButton: Button
    private lateinit var weekText: TextView
    private lateinit var nextButton: Button

    /**
     * Whether or not the activity is in tablet mode.
     */
    private var tablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workdayDate = DateTime.parse(it.getString(ARG_WORKDAY_DATE))
        }

        // Initialize shared preferences
        sharedPrefs = activity!!.getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        // Instantiate viewModel
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)

        setHasOptionsMenu(true)

        // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
        // If this view is present, then the fragment should be in tablet mode.
        if (activity!!.main_detail_container != null)
            tablet = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentDateSelectorBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_date_selector, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!tablet) {
            dateSelectorMinusTwo = view.findViewById(R.id.dateSelectorMinusTwo)
            dateSelectorMinusOne = view.findViewById(R.id.dateSelectorMinusOne)
            dateSelectorNow = view.findViewById(R.id.dateSelectorNow)
            dateSelectorPlusOne = view.findViewById(R.id.dateSelectorPlusOne)
            dateSelectorPlusTwo = view.findViewById(R.id.dateSelectorPlusTwo)

            // Instantiate a ViewPager and a PagerAdapter.
            mPager = view.findViewById(R.id.pager)

            // The pager adapter, which provides the pages to the view pager widget.
            val pagerAdapter = PagerAdapter(childFragmentManager)
            mPager.adapter = pagerAdapter
            mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {}

                override fun onPageSelected(position: Int) {
                    val date = workdayDate!!.minusDays(29 - position)
                    // Shows correct dates in buttons
                    dateSelectorMinusTwo.setDate(date.minusDays(2))
                    dateSelectorMinusOne.setDate(date.minusDays(1))
                    dateSelectorNow.setDate(date)
                    dateSelectorPlusOne.setDate(date.plusDays(1))
                    dateSelectorPlusTwo.setDate(date.plusDays(2))
                }
            })
            mPager.currentItem = 29

            // OnClickListeners buttons
            dateSelectorMinusTwo.setOnClickListener {
                mPager.arrowScroll(View.FOCUS_LEFT)
                mPager.arrowScroll(View.FOCUS_LEFT)
            }
            dateSelectorMinusOne.setOnClickListener {
                mPager.arrowScroll(View.FOCUS_LEFT)
            }
            dateSelectorPlusOne.setOnClickListener {
                mPager.arrowScroll(View.FOCUS_RIGHT)
            }
            dateSelectorPlusTwo.setOnClickListener {
                mPager.arrowScroll(View.FOCUS_RIGHT)
                mPager.arrowScroll(View.FOCUS_RIGHT)
            }
        } else {
            previousButton = view.findViewById(R.id.buttonPreviousWeek)
            weekText = view.findViewById(R.id.textWeekNumber)
            nextButton = view.findViewById(R.id.buttonNextWeek)

            var date = DateTime.now()
            // Load all day views
            loadWeek(date)

            // OnClickListeners buttons
            previousButton.setOnClickListener {
                date = date.minusDays(7)
                loadWeek(date)
            }
            nextButton.setOnClickListener {
                date = date.plusDays(7)
                loadWeek(date)
            }
        }
    }

    private fun loadWeek(date: DateTime) {
        // Get days of week
        val monday = date.minusDays(date.dayOfWeek-1)
        val tuesday = monday.plusDays(1)
        val wednesday = tuesday.plusDays(1)
        val thursday = wednesday.plusDays(1)
        val friday = thursday.plusDays(1)
        val saturday = friday.plusDays(1)
        val sunday = saturday.plusDays(1)

        // Load DayFragments
        fragmentManager!!.beginTransaction().apply {
            replace(R.id.day_monday, DayFragment.newInstance(monday))
            replace(R.id.day_tuesday, DayFragment.newInstance(tuesday))
            replace(R.id.day_wednesday, DayFragment.newInstance(wednesday))
            replace(R.id.day_thursday, DayFragment.newInstance(thursday))
            replace(R.id.day_friday, DayFragment.newInstance(friday))
            //replace(R.id.day_saturday, DayFragment.newInstance(saturday)) TODO
            //replace(R.id.day_sunday, DayFragment.newInstance(sunday)) TODO
        }.commit()

        // Week text
        weekText.text = getString(
            R.string.dates_interval,
            if (monday.monthOfYear == sunday.monthOfYear) { monday.toString("d") } else { monday.toString("d MMMM") },
            sunday.toString("d MMMM")
        )
    }

    private inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment {
            // Gets date to show first
            val date = workdayDate!!.minusDays(29-position)
            // Loads DayFragment
            return DayFragment.newInstance(date)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.main, menu)

        // Hide userSelector button if no admin permissions
        if (!sharedPrefs.getBoolean("ADMIN", false)) {
            val item = menu.findItem(R.id.action_userSelector)
            item.isVisible = false
            activity!!.invalidateOptionsMenu()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        if (!tablet)
            mPager.adapter = null
        super.onSaveInstanceState(savedInstanceState)
    }

}
