package suer_fi.developmentkitapp.tabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import suer_fi.developmentkitapp.R

/**
 * Created by sure-fi on 2/7/18.
 */
class Settings : Fragment() {

    companion object {
        val PAGE_NUM = "PAGE_NUM"

        fun newInstance(): Settings {

            return Settings()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings, container, false)
        return view
    }
}