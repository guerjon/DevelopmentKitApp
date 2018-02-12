package suer_fi.developmentkitapp.listeners

import kotlinx.android.synthetic.main.other_settings.*
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text
import suer_fi.developmentkitapp.R

/**
 * Created by sure-fi on 2/8/18.
 */
class OtherSettingsListener (general_view: LinearLayout){

    var general_view = general_view

    fun setGeneralView(){

    }

    fun getFirstLedListener() : AdapterView.OnItemSelectedListener{

        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.first_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getSecondLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.second_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getThirdLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.third_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getFourthLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.fourth_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getFifthLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.fifth_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getSixthLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                general_view.findViewById<TextView>(R.id.sixth_led_tag).text = position.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getQOSListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getHoldTimeButtonListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getConfigListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getRxLedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getTxListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}