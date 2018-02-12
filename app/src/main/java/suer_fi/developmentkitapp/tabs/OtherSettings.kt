package suer_fi.developmentkitapp.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import suer_fi.developmentkitapp.R

/**
 * Created by sure-fi on 2/6/18.
 */
class OtherSettings : Fragment(){

    companion object {
        fun newInstance(): OtherSettings {
            return OtherSettings()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.other_settings, container, false)
        return view
    }
}