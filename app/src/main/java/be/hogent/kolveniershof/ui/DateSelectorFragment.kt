package be.hogent.kolveniershof.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.hogent.kolveniershof.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DateSelectorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DateSelectorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateSelectorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_selector, container, false)
    }
}
