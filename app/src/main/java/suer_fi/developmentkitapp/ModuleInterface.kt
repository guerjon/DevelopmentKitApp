package suer_fi.developmentkitapp

import android.app.PendingIntent.getActivity
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.os.Bundle
import android.support.v4.app.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_module_interface.*
import android.util.Log
import android.util.DisplayMetrics
import android.widget.*
import android.widget.AdapterView
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.sp
import android.support.v4.widget.NestedScrollView
import kotlinx.android.synthetic.main.other_settings.*
import suer_fi.developmentkitapp.listeners.*
import suer_fi.developmentkitapp.tabs.*

class ModuleInterface : FragmentActivity() {

    private var device: BluetoothPeripheral? = null
    private var bluetoothGattCallback : BluetoothGattCallback? = null
    private var bluetoothGatt : BluetoothGatt? = null
    private var other_settings_listeners : OtherSettingsListener? = null
    private var command_listeners : CommandsListener? = null
    private var status_listener: StatusListener? = null
    private var interrupt_bits_listener: InterruptBitsListener? = null

    companion object {
        var fragmentList : ArrayList<Fragment> = ArrayList()
        var tab_titles = arrayOf("Radio Settings","Other Settings","Commands","Status","Interrupt Bits")
        var height = 0
        var width = 0
        var avaible_space = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_interface)

        Log.d("DEBUG","onCreate")
        hideBarNavigation()
        initDevice()
        initSizes()
        initTabs()

    }

    private fun initSizes(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
    }

    private fun initTabs(){
        fragmentList.add(Settings.newInstance())
        fragmentList.add(OtherSettings.newInstance())
        fragmentList.add(Commands.newInstance())
        fragmentList.add(Status.newInstance())
        fragmentList.add(InterruptBits.newInstance())

        view_pager.adapter =  MyAdapter(supportFragmentManager) //setup the apdater
        tabs.setupWithViewPager(view_pager) // show the tabs

        // Gets the layout params that will allow you to resize the layout
        val params = view_pager.layoutParams
        val tabs_params = tabs.layoutParams
        val fotter_params = footer.layoutParams

        // Changes the height and width to the specified *pixels*

        avaible_space = height - (tabs_params.height + fotter_params.height + 50)
        params.height = avaible_space
        params.width = width
        view_pager.setLayoutParams(params)
    }

    /** Called when the user taps the Send button  */
    fun changeMode(view: View) {
        transmit_section.visibility = View.GONE
        recieve_section.visibility = View.VISIBLE
    }

    fun changeToTransmitSection(view: View){
        recieve_section.visibility = View.GONE
        transmit_section.visibility = View.VISIBLE
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
        showMainTabContainer()
        setValuesToView()
    }

    private fun showConnectingLayout(){
        var scanning_div = findViewById<LinearLayout>(R.id.scanning_div)
       runOnUiThread{
            scanning_div.visibility = View.VISIBLE
       }
    }

    private fun hideConnectingLayout(){
        var scanning_div = findViewById<LinearLayout>(R.id.scanning_div)
        runOnUiThread{
            scanning_div.visibility = View.GONE
        }
    }

    private fun showMainTabContainer(){
        Log.d("DEBUG","showMainTabContainer")
        var container_form =  findViewById<LinearLayout>(R.id.container_form)
        runOnUiThread{
            var layout_params = container_form.layoutParams
            if(avaible_space != 0){
                layout_params.height = avaible_space
                container_form.layoutParams = layout_params
                container_form.visibility = View.VISIBLE
            }else{
                layout_params.height = 800
                container_form.layoutParams = layout_params
                container_form.visibility = View.VISIBLE
            }
        }
    }

    private fun setValuesToView(){
        initSettingsTab()
        initOtherSettingsTab()
    }

    private fun initSettingsTab(){
        val settings_listeners  = SettingsListener()
        setUpSpinner(transmit_power_snnipper,Constants.TRANSMIT_POWER_OPTIONS, settings_listeners.getTransmitPowerSelectedListener())
        setUpSpinner(polarity_snnipper,Constants.POLARITY_OPTIONS,settings_listeners.getPolaritySelectedListener())
        setUpSpinner(num_retries_spinner,Constants.NUM_RETRIES_OPTIONS,settings_listeners.getNumRetriesListener())
        setUpSpinner(radio_spinner,Constants.RADIO_MODE_OPTIONS,settings_listeners.getRadioModuleListener())
        setUpSpinner(spreading_factor_snniper,Constants.SPREADING_FACTOR,settings_listeners.getSpreadingFactorListener())
        setUpSpinner(band_width_snniper,Constants.BANDWIDTH,settings_listeners.getBandwidthListener())
    }

    private fun initOtherSettingsTab(){
        var view = findViewById<LinearLayout>(R.id.other_setings_main_tablayout)

        var other_settings_listeners = OtherSettingsListener(view)
        setUpSpinner(first_led_spinner,Constants.INDICATIONS,other_settings_listeners.getFirstLedListener())
        setUpSpinner(second_led_spinner,Constants.INDICATIONS,other_settings_listeners.getSecondLedListener())
        setUpSpinner(third_led_spinner,Constants.INDICATIONS,other_settings_listeners.getThirdLedListener())
        setUpSpinner(fourth_led_spinner,Constants.INDICATIONS,other_settings_listeners.getFourthLedListener())
        setUpSpinner(fifth_led_spinner,Constants.INDICATIONS,other_settings_listeners.getFifthLedListener())
        setUpSpinner(sixth_led_spinner,Constants.INDICATIONS,other_settings_listeners.getSixthLedListener())

        setUpSpinner(qos_spinner,Constants.QOS,other_settings_listeners.getQOSListener())
        setUpSpinner(button_hold_time_spinner,Constants.BUTTON_HOLD_TIME,other_settings_listeners.getHoldTimeButtonListener())
        setUpSpinner(button_config_spinner,Constants.BUTTON_CONFIG,other_settings_listeners.getConfigListener())
        setUpSpinner(rx_led_mode_spinner,Constants.RX_LED,other_settings_listeners.getRxLedListener())
        setUpSpinner(tx_led_mode_spinner,Constants.TX_LED,other_settings_listeners.getTxListener())
    }

    private fun initCommandsTab(){
        var command_listeners = CommandsListener()

    }

    private fun initStatusTab(){
        var status_listener = StatusListener()
    }

    private fun interruptBitsTab(){
        var interrupt_bits_listener = InterruptBitsListener()
    }

    private fun setUpSpinner(spinner:Spinner,items: Array<String>,listener: AdapterView.OnItemSelectedListener){
        runOnUiThread{
            spinner.adapter = getSpinnerAdapter(items)
            spinner.onItemSelectedListener = listener
        }
    }

    private fun getSpinnerAdapter(elements: Array<String>): ArrayAdapter<String>{
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
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

    class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragmentList.count()
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tab_titles[position]
        }
    }
}
