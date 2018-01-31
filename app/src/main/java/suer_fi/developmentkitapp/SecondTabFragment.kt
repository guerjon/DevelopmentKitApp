package suer_fi.developmentkitapp

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_page.*

/**
 * Created by sure-fi on 1/31/18.
 */
class SecondTabFragment : Fragment(){



    companion object {
        val PAGE_NUM = "PAGE_NUM"
        fun newInstance(): SecondTabFragment{
            return SecondTabFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.second_tab_fragment, container, false)
        val page = getArguments().getInt(PAGE_NUM)
        return view
    }


}