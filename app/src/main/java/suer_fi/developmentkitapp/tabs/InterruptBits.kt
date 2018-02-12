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
class InterruptBits : Fragment(){
    companion object {
        fun newInstance(): InterruptBits {
            return InterruptBits()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.interrupt_bits, container, false)
        return view
    }
}