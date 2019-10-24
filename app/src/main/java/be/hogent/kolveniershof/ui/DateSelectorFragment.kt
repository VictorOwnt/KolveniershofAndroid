package be.hogent.kolveniershof.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import be.hogent.kolveniershof.R

private const val ARG_WORKDAY_DATE = "workdayDate"

class DateSelectorFragment : Fragment() {

    private var workdayDate : String? = null

    companion object {
        @JvmStatic
        fun newInstance(workdayId: String?) =
            DateSelectorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKDAY_DATE, workdayId)
                }
            }
    }

    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workdayDate = it.getString(ARG_WORKDAY_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_selector, container, false)
    }
}
