package suer_fi.developmentkitapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.main_device_activity.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import java.util.*
import kotlin.concurrent.schedule

class Devices : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback{

    private var devices: ArrayList<BluetoothPeripheral> = ArrayList()
    private var mRecyclerView : RecyclerView? = null
    private var mAdapter : DevicesAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var bluetoothManager: BluetoothManager? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothScanner: BluetoothLeScanner? = null
    private var menu: Menu? = null
    private var scanButton : MenuItem? = null
    private var stopScanButton : MenuItem? = null
    private var scanCallback : ScanCallback? = null

    companion object {
        val REQUEST_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_device_activity)
        checkPermissionsStatus()
    }

    private fun initRecyclerViewList(){
        mRecyclerView = my_recycler_view
        mLayoutManager = LinearLayoutManager(this)

        if(bluetoothScanner == null){
            initBluetooth()
        }

        mAdapter = DevicesAdapter(devices,this)
        mRecyclerView?.layoutManager = mLayoutManager
        mRecyclerView?.adapter = mAdapter
    }

    private fun initBluetoothManager(){
        if(bluetoothManager == null){
            bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        }
    }

    private fun initBluetoothAdapter(){
        if(bluetoothManager != null){
            bluetoothAdapter = bluetoothManager?.adapter
        }
    }

    private fun initBluetoothScanner(){
        if(bluetoothAdapter != null){
            bluetoothScanner = bluetoothAdapter?.bluetoothLeScanner
        }
    }

    private fun initBluetooth(){
        initBluetoothManager()
        initBluetoothAdapter()
        initBluetoothScanner()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_devices,menu);
        this.menu = menu
        this.scanButton = menu?.findItem(R.id.scan)
        this.stopScanButton = menu?.findItem(R.id.stop_scan)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item?.itemId

        if(id == R.id.scan){
            updateViewAfterStartScan()
            this.scanForDevices()
        }

        if(id == R.id.stop_scan){
            hideStopScanButton()
            showScanButton()
           this.stopScan()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissionsStatus(){
        Log.d("DEBUG","checkPermissionsStatus")
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                alert("In order to use the Bluetooth we will need activate the location services on the app.") {
                    title = "Location services required"
                    okButton {
                        requestLocationPermission()
                    }
                }.show()

            } else {
                requestLocationPermission()
            }
        }else{
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBluetoothAdapter == null) {
                alert { "The device does not support Bluetooth." }.show()
            } else {
                if (!mBluetoothAdapter.isEnabled) {
                    requestBluetooth()
                }else{
                    prepareForScan()
                }
            }
        }
    }

    private fun prepareForScan(){
        initBluetooth()
        hideLocationServicesNeededScreen()
        hideBluetoothServicesNeededScreen()
        hideScanButton()
        showStopScanButton()
        showScanActivity()
        scanForDevices()
        initRecyclerViewList() // always should be called after initBluetooth
    }

    fun activateLocationsBtnPressed(view: View){
        requestLocationPermission()
    }

    fun activityBluetoothBtnPressed(view: View){
        requestBluetooth()
    }

    fun requestLocationPermission(){
        Log.d("DEBUG","requestLocationPermission")
        val permissionGranted = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if(permissionGranted){
            hideLocationServicesNeededScreen()
            requestBluetooth()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d("DEBUG","onRequestPermissionsResult ${requestCode}")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == 0){
            hideLocationServicesNeededScreen()
            requestBluetooth()
        }else{
            showLocationServicesNeededScreen()
        }
    }

    private fun showLocationServicesNeededScreen(){
        var layout = findViewById<LinearLayout>(R.id.activate_location_layout)
        layout.visibility = View.VISIBLE
    }

    private fun hideLocationServicesNeededScreen(){
        var layout = findViewById<LinearLayout>(R.id.activate_location_layout)
        layout.visibility = View.GONE
    }

    private fun showBluetoothServicesNeededScreen(){
        findViewById<LinearLayout>(R.id.activate_bluetooth_layout).visibility = View.VISIBLE
    }

    private fun hideBluetoothServicesNeededScreen(){
        findViewById<LinearLayout>(R.id.activate_bluetooth_layout).visibility = View.GONE
    }

    fun requestBluetooth(){
        Log.d("DEBUG","requestBluetooth")
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        val request_enabled_bt = 1
        startActivityForResult(enableBtIntent, request_enabled_bt)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("DEBUG","onActivityResult ${requestCode} ${resultCode} ")
        if(resultCode == Activity.RESULT_OK){
            prepareForScan()
        }else{
            showBluetoothServicesNeededScreen()
        }
    }

    fun showScanActivity(){
        var layer = findViewById<LinearLayout>(R.id.scanning_div)
        layer.visibility = View.VISIBLE
    }

    fun hideScanActivity(){
        var layer = findViewById<LinearLayout>(R.id.scanning_div)
        layer.visibility = View.GONE
    }

    fun hideStopScanButton(){
        menu?.findItem(R.id.stop_scan)?.setVisible(false)
    }

    fun showStopScanButton(){
        menu?.findItem(R.id.stop_scan)?.setVisible(true)
    }

    fun showScanButton(){
        menu?.findItem(R.id.scan)?.setVisible(true);
    }

    fun hideScanButton(){
        menu?.findItem(R.id.scan)?.setVisible(false);
    }

    private fun updateViewAfterStopScan(){
        showScanButton()
        hideStopScanButton()
        hideScanActivity()
    }

    private fun updateViewAfterStartScan(){
        hideScanButton()
        showStopScanButton()
        showScanActivity()
        clearDevices()
    }

    private fun clearDevices(){
        devices.clear()
    }

    private fun scanForDevices(){
        Log.d("DEBUG","scanForDevices")

        scanCallback = object: ScanCallback(){
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                if(result.device.name != null){
                    if(result.device.name.contains("Sure-Fi")){
                        val bytes = result.scanRecord.manufacturerSpecificData

                        var manufacturer_data = ""

                        if (bytes.valueAt(0) != null) {
                            manufacturer_data= bytes.valueAt(0).toHex()
                        }

                        val hardware_type = manufacturer_data.substring(IntRange(0,1))
                        val device_status = manufacturer_data.substring(IntRange(6,7) )
                        val device_id = manufacturer_data.substring(IntRange(7,13))
                        val paired_id = manufacturer_data.substring(IntRange(13,19))
                        var contains = false

                        for (temp_device in devices){
                            if(temp_device.device_id == device_id)
                                contains = true
                        }

                        if(!contains){
                            val device = BluetoothPeripheral(
                                    address = result.device.address,
                                    name = result.device.name,
                                    hardware_type = hardware_type,
                                    device_status = device_status,
                                    device_id = device_id,
                                    paired_id = paired_id,
                                    manufacturer_data = manufacturer_data,
                                    bluetooth_device = result.device
                            )

                            devices.add(device)
                            mAdapter?.notifyItemInserted(devices.size - 1)
                            //Log.d("ScanDeviceActivity","${device.hardware_type} ${device.device_status} ${device.device_id} ${device.paired_id}")
                        }
                    }
                }
            }
        }

        bluetoothScanner?.startScan(scanCallback)
        this.stopScanAfterSeconds()
    }

    private fun stopScanAfterSeconds(time : Long = 20000){
        Log.d("DEBUG","stopScanAfterSeconds")
        Timer().schedule(time){
            Log.d("DEBUG","111")
            stopScan()
            Log.d("DEBUG","111")
        }
    }

    fun stopScan(){
        Log.d("DEBUG","stopScan()")
        if(bluetoothScanner != null){
            bluetoothScanner?.stopScan(scanCallback)
            runOnUiThread {
              updateViewAfterStopScan()
            }
        }else{
            toast("Error the scanner can't be stoped")
        }
    }

}
