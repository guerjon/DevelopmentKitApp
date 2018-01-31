package suer_fi.developmentkitapp

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_module_interface.*
import kotlinx.android.synthetic.main.fragment_module_interface.view.*
import org.jetbrains.anko.alert

import android.util.Log
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import suer_fi.developmentkitapp.R.id.scanning_div


class ModuleInterface : AppCompatActivity() {

    private var device: BluetoothPeripheral? = null
    private var bluetoothGattCallback : BluetoothGattCallback? = null
    private var bluetoothGatt : BluetoothGatt? = null
    private var mainTabFragment: MainTabFragment? = null
    private var secondTabFragment: SecondTabFragment? null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_interface)
        Log.d("DEBUG","onCreate")

        initDevice()
        initTabs()

/*        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
*/
    }

    private fun initTabs(){
        val pageAdapter = PageAdapter(supportFragmentManager) // getSupportFragmentManager() this method return a FragmentManager object

        pageAdapter.add(MainTabFragment.newInstance(),"Main")
        pageAdapter.add(SecondTabFragment.newInstance(),"Second")

        view_pager.adapter = pageAdapter
        tabs.setupWithViewPager(view_pager) // it set the tabs to the view
    }

    private fun hideBarNavigation(){
        var decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }

    private fun initDevice(){
        Log.d("DEBUG","initDevice")

        device = intent.getParcelableExtra<BluetoothPeripheral>("device")

        if(device != null){
            if(device is BluetoothPeripheral){
                connectDevice()
            }else{
                Log.d("DEBUG","init device fail")
            }
        }else{
            Log.d("DEBUG","init device fail, the device is null")
        }
    }

    private fun processDeviceConnected(){

    }

    private fun connectDevice(){
        Log.d("DEBUG","connectDevice")
        if(device != null){
            bluetoothGattCallback = setUpBluetoothGattCallback()
            bluetoothGatt = device?.bluetooth_device?.connectGatt(this,true,bluetoothGattCallback)
        }else{
            Log.w("DEBUG","The device wasn't correctly transfer to the module interface.")
        }
    }

    private fun connectedDevice(){
        Log.d("DEBUG","connectedDevice")
        hideConnectingLayout()
    }

    private fun showConnectingLayout(){
      /*  runOnUiThread{
            scanning_div.visibility = View.VISIBLE
        }
        */
    }

    private fun hideConnectingLayout(){
        /*runOnUiThread{
            scanning_div.visibility = View.GONE
        }*/
    }

    private fun disconnectedDevice(){
        Log.d("DEBUG","disconnectedDevice")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_module_interface, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun discoverServices(){
        bluetoothGatt?.discoverServices()
    }

    private fun setUpBluetoothGattCallback() : BluetoothGattCallback{

        return object : BluetoothGattCallback (){

            override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
                super.onCharacteristicChanged(gatt, characteristic)
                Log.d("DEBUG","onCharacteristicChanged")
            }

            override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
                super.onCharacteristicRead(gatt, characteristic, status)
                Log.d("DEBUG","onCharacteristicRead")
            }

            override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
                super.onCharacteristicWrite(gatt, characteristic, status)
                Log.d("DEBUG","onConnectionStateChange")
            }

            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                Log.d("DEBUG","onConnectionStateChange status: ${status}  ||  new_state : ${newState}")

                when(newState){
                    BluetoothProfile.STATE_CONNECTED -> {
                        connectedDevice()
                    }

                    BluetoothProfile.STATE_DISCONNECTED -> {
                        disconnectedDevice()
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                Log.d("DEBUG","onServicesDiscovered")
                if (status === BluetoothGatt.GATT_SUCCESS) {
                    // Set notifications on this service.

                    val services = gatt?.services

                    for (service in services.orEmpty()){
                        Log.d("DEBUG",service.uuid.toString())
                    }
                } else {
                    Log.w("DEBUG", "onServicesDiscovered received: " + status)
                }
            }
        }
    }

}
