package com.hmproductions.coronacalculator

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.toast
import java.lang.Math.*

class MainActivity : AppCompatActivity() {

    val g_o = 21.2F
    val currentYear = 2018F

    private var relativeAirDensity: Float? = null
    private var Vv: Float? = null
    private var Vc: Float? = null
    private var acLoss: Float? = null
    private var dcLoss: Float? = null
    private var m_v: Float? = null

    private var pressure = 0.0F
    private var temperature = 0.0F
    private var radius = 0.0F
    private var distance = 0.0F
    private var voltage = 0.0F
    private var frequency = 0.0F
    private var subConductors = 0.0F
    private var height = 0.0F
    private var spacing = 0.0F
    private var maxSurfaceGrad = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({contentView?.hideKeyboard() }, 200)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.calculation_action -> calculate()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sanityChecks(): Boolean {
        if (temperatureEditText.text.toString().isBlank() || radiusEditText.text.toString().isBlank() ||
            pressureEditText.text.toString().isBlank() || distanceEditText.text.toString().isBlank() ||
            frequencyEditText.text.toString().isBlank() || voltageEditText.text.toString().isBlank() ||
            poleSpacingEditText.text.toString().isBlank() || meanHeightEditText.text.toString().isBlank()
        ) {
            toast("Please fill all fields")
            return false
        }

        if (weatherRadioGroup.checkedRadioButtonId == -1) {
            toast("Select weather type")
            return false
        }

        return true
    }

    private fun calculate() {
        if (!sanityChecks()) return

        temperature = temperatureEditText.text.toString().toFloat()
        pressure = pressureEditText.text.toString().toFloat()
        radius = radiusEditText.text.toString().toFloat()
        distance = distanceEditText.text.toString().toFloat()
        voltage = (voltageEditText.text.toString().toFloat() / sqrt(3.0)).toFloat()
        frequency = frequencyEditText.text.toString().toFloat()

        subConductors = noOfSubConductorsEditText.text.toString().toFloat()
        spacing = poleSpacingEditText.text.toString().toFloat()
        height = meanHeightEditText.text.toString().toFloat()
        maxSurfaceGrad = maxSurfaceGradientEditText.text.toString().toFloat()

        m_v = (exp((-exp(1.0)) * (currentYear - yearsEditText.text.toString().toFloat()) / 100)).toFloat()
        relativeAirDensity = (3.92F * pressure) / (273 + temperature)
        Vc = (radius * g_o * relativeAirDensity!! * m_v!! * log(100F * distance.toDouble() / radius)).toFloat()
        Vv =
                (radius * g_o * relativeAirDensity!! * m_v!! * (1F + 0.3F / sqrt(radius.toDouble() * relativeAirDensity!!)) * log(
                    100F * distance.toDouble() / radius
                )).toFloat()

        if (weatherRadioGroup.checkedRadioButtonId == R.id.stormRadioButton) {
            Vc = Vc!! * 0.8F
            Vv = Vv!! * 0.8F
        }

        acLoss = ((240F / relativeAirDensity!!) * (25F + frequency) * sqrt(
            radius.toDouble() /
                    (100F * distance).toDouble()
        ) * (voltage - Vc!!) * (voltage - Vc!!) * pow(10.toDouble(), (-5).toDouble())).toFloat()

        val newDel = 3.92F * pressure / 75.01 / (273 + temperature)
        dcLoss =
                (2 * voltage * ((2.toDouble() / PI) * atan(2 * height.toDouble() / spacing) + 1) * 0.25 * subConductors * radius / 100 *
                        pow(2.toDouble(), 0.25 * (maxSurfaceGrad - g_o * newDel)) * pow(
                    10.toDouble(),
                    (-3).toDouble()
                )).toFloat()

        val dcLossRatioVar = Array(100) { _ -> 0F }

        for (i in 1..100) {
            dcLossRatioVar[i - 1] =
                    (2 * voltage * ((2.toDouble() / PI) * atan(2 / i.toDouble()) + 1) * 0.25 * subConductors * radius / 100 *
                            pow(2.toDouble(), 0.25 * (maxSurfaceGrad - g_o * newDel)) * pow(
                        10.toDouble(),
                        (-3).toDouble()
                    )).toFloat()
        }

        var leastRatio = 100
        for (i in 1..100) {
            if (dcLossRatioVar[i - 1] - dcLossRatioVar[i] < 0.001) {
                leastRatio = i
                break
            }
        }

        visualVoltageTextView.text = Vv.toString()
        criticalVoltagetextView.text = Vc.toString()
        acLossTextView.text = acLoss.toString()
        dcLossTextView.text = dcLoss.toString()
        ratioTextView.text = "1:$leastRatio"
        maximumRadiusLossTextView.text = "N/A"
    }

    fun View.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
