package be.hogent.kolveniershof.ui


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.databinding.FragmentDayBinding
import be.hogent.kolveniershof.viewmodels.DayViewModel

private const val ARG_WORKDAY_ID = "workdayId"

class DayFragment : Fragment() {

    private var workdayId : String? = null

    companion object {
        @JvmStatic
        fun newInstance(workdayId: String?) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORKDAY_ID, workdayId)
                }
            }
    }

    private lateinit var viewModel: DayViewModel
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workdayId = it.getString(ARG_WORKDAY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)

        // Inflate the layout for this fragment
        val binding: FragmentDayBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_day, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.dag_naam_test)
        sharedPrefs = activity!!.getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)

        arguments?.getString("workdayId")?.let { viewModel.getWorkdayById(it) }
        viewModel.workday.removeObservers(this)
        viewModel.workday.observe(this, Observer { workday ->
            // TODO hier code voor verbegen elementen en andere dergelijke zaken
        })
    }


}
