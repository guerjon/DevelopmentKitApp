package suer_fi.developmentkitapp

import android.bluetooth.BluetoothDevice
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by sure-fi on 1/23/18.
 */




open class BluetoothPeripheral(
        val address: String,
        val name: String,
        val device_id: String,
        val hardware_type: String,
        val device_status: String,
        val firmware_version: String? = null,
        val paired_id: String? = null,
        val security_string : String? = null,
        val manufacturer_data : String? = null,
        var bluetooth_device : BluetoothDevice? = null

): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(BluetoothDevice::class.java.classLoader)) {}


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(name)
        parcel.writeString(device_id)
        parcel.writeString(hardware_type)
        parcel.writeString(device_status)
        parcel.writeString(firmware_version)
        parcel.writeString(paired_id)
        parcel.writeString(security_string)
        parcel.writeString(manufacturer_data)
        parcel.writeParcelable(bluetooth_device, flags)
    }


    private fun calculateSecurityString() : ByteArray {

        var peripheralRXUUID = this.device_id.toUpperCase().reversed()
        var peripheralTXUUID = this.paired_id?.toUpperCase() + "x~sW5-C\"6fu>!!~X"
        var final_string = peripheralRXUUID + peripheralTXUUID

        return final_string.md5().toByteArray()
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{BluetoothPeripheral(it)}
    }

    override fun describeContents() = 0
}

