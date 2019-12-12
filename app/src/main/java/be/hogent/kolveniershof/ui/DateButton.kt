package be.hogent.kolveniershof.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import be.hogent.kolveniershof.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.joda.time.DateTime
import java.util.*

class DateButton(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

     val dateTextView: TextView
     val monthTextView: TextView

    init {
        inflate(context, R.layout.view_date_button, this)

        dateTextView = findViewById(R.id.dateButtonDate)
        monthTextView = findViewById(R.id.dateButtonMonth)
        val layout: ConstraintLayout = findViewById(R.id.dateButtonLayout)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DateButton)
        dateTextView.text = attributes.getString(R.styleable.DateButton_date)
        monthTextView.text = attributes.getString(R.styleable.DateButton_month)

        val selected = attributes.getBoolean(R.styleable.DateButton_selected, false);
        if (!selected) {
            dateTextView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            monthTextView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            layout.stateListAnimator = null
        }

        attributes.recycle()
    }

    /**
     * Set string values from given date
     *
     * @param date
     */
    fun setDate(date: DateTime) {
        dateTextView.text = date.toString("dd")
        monthTextView.text = date.toString("MMM")
    }

}