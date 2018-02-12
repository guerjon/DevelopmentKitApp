package suer_fi.developmentkitapp.listeners

import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_module_interface.*

/**
 * Created by sure-fi on 2/8/18.
 */
class SettingsListener {

    fun getTransmitPowerSelectedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getPolaritySelectedListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getNumRetriesListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getRadioModuleListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getSpreadingFactorListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun getBandwidthListener() : AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}