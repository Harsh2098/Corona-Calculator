package com.hmproductions.coronacalculator

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.hmproductions.coronacalculator.utils.Miscellaneous
import kotlinx.android.synthetic.main.activity_graph.*
import java.util.*

class GraphActivity : AppCompatActivity(), Miscellaneous.GetGraphDetails {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        title = "Corona Characteristics"
        setSupportActionBar(graph_toolbar)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        contentViewPager.adapter = ContentAdapter(supportFragmentManager)
        tabHeadings.setupWithViewPager(contentViewPager)

        tabHeadings.setTabTextColors(
            ContextCompat.getColor(this, R.color.white),
            ContextCompat.getColor(this, R.color.gold)
        )
    }

    override fun getGraphDetails(graph: Int): ArrayList<Entry> {
        return when (graph) {
            0 -> intent.getParcelableArrayListExtra(RATIO_ENTRIES)
            1 -> intent.getParcelableArrayListExtra(RADIUS_ENTRIES)
            2 -> intent.getParcelableArrayListExtra(PRESSURE_ENTRIES)
            else -> intent.getParcelableArrayListExtra(TEMPERATURE_ENTRIES)
        }
    }

    companion object {

        val RATIO_ENTRIES = "ratio-entries"
        val RADIUS_ENTRIES = "radius-entries"
        val TEMPERATURE_ENTRIES = "temperature-entries"
        val PRESSURE_ENTRIES = "pressure-entries"
    }
}
