package suer_fi.developmentkitapp

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_page.*

/**
 * Created by sure-fi on 1/31/18.
 */
class MainTabFragment : Fragment() {

    private var device: BluetoothPeripheral? = null
    private var bluetoothGattCallback : BluetoothGattCallback? = null
    private var bluetoothGatt : BluetoothGatt? = null


    companion object {
        val PAGE_NUM = "PAGE_NUM"

        fun newInstance(): MainTabFragment {

            /*val fragment = MainTabFragment()
            val args = Bundle()
            args.putInt(PAGE_NUM, page)
            fragment.setArguments(args)*/
            return MainTabFragment()
        }
    }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.main_tab_fragment, container, false)
            val page = getArguments().getInt(PAGE_NUM)
            return view
        }
}