package suer_fi.developmentkitapp

import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.device_list_item.view.*

/**
 * Created by sure-fi on 1/24/18.
 */
class DevicesAdapter(
        private val devices: ArrayList<BluetoothPeripheral>,
        val context: Context) : RecyclerView.Adapter<DevicesAdapter.DeviceHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        val inflatedView = parent.inflate(R.layout.device_list_item, false)
        return DeviceHolder(inflatedView,context)
    }

    override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
        val itemDevice = devices[position]
        holder.bindInfo(itemDevice)
        holder.bindInfo(itemDevice)
    }

    override fun getItemCount() = devices.size


    class DeviceHolder(v: View,context:Context) : RecyclerView.ViewHolder(v), View.OnClickListener{
        private var view = v
        private var device:BluetoothPeripheral? = null
        private var context = context

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            stopDevicesScan()

            var bluetoothPeripheral : BluetoothPeripheral = device as BluetoothPeripheral
            var i = Intent(context,ModuleInterface::class.java)

            i.putExtra("device",bluetoothPeripheral)
            context.startActivity(i)
        }

        private fun stopDevicesScan(){
            Log.d("DEBUG",device?.name)
            var devices = context as Devices
            if(devices is Devices){
                devices.stopScan()
            }else{
                Log.w("DEBUG","Error Callback to the scanner references loose.")
            }
        }


        /*
        * @return the return structure have the next form ["Name of the module", "Background color of the module","color text"]
        * */
        private fun choseTypesAttributes(hardware_type:String) : Array<Any>{

            when(hardware_type){
                "00" -> return  arrayOf("Sure-Fi Module",R.drawable.sure_fi_module_rectangle,R.color.colorWhite)
                "01" -> return arrayOf("Wiegand Central",R.drawable.wiegand_central_rectangle,R.color.colorWhite)
                "02" -> return arrayOf("Wiegand Remote",R.drawable.wiegand_remote_rectangle,R.color.colorWiegandCentral)
                "03" -> return arrayOf("HVAC Thermostat",R.drawable.hvac_thermostat_rectangle,R.color.colorWhite)
                "04" -> return arrayOf("HVAC Equipment",R.drawable.hvac_equipment_rectangle,R.color.colorHVACThermostat)
                "05" -> return arrayOf("Serial Data Central",R.drawable.serial_data_central_rectangle,R.color.colorWhite)
                "06" -> return arrayOf("Serial Data Remote",R.drawable.serial_data_remote_rectangle,R.color.colorWhite)
                else -> return arrayOf("No option found to ${hardware_type} type",R.color.colorUknownType,R.color.colorWhite)
            }
        }

        fun bindInfo(device: BluetoothPeripheral){
            val types = choseTypesAttributes(device.hardware_type)
            this.device = device
            view.name.text = device.name
            view.device_id.text = device.device_id
            view.manufacturer_data.text = device.manufacturer_data
            view.hardware_type.text = types.get(0) as String
            view.hardware_type.setBackgroundResource(types.get(1) as Int);
            view.hardware_type.setTextColor(ContextCompat.getColor(context,types.get(2) as Int))
        }

        companion object {
            //5
            private val DEVICE_KEY = "DEVICE"
        }
    }
}