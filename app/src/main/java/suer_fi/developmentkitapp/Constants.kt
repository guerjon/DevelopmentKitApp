package suer_fi.developmentkitapp

/**
 * Created by sure-fi on 1/25/18.
 */
class Constants {
    companion object {
        val MODULE_TYPE = "00"
        val WIEGAND_TYPE = "01"
        val REMOTE_TYPE = "02"
        val HVAC_THERMOSTAT_TYPE = "03"
        val HVAC_EQUIPMENT_TYPE = "04"
        val SERIAL_DATA__CENTRAL_TYPE = "05"
        val SERIAL_DATA_REMOTE_TYPE = "06"
        val TRANSMIT_POWER_OPTIONS = arrayOf(
                "0 dBm (1 mW)",
                "1 dBm",
                "2 dBm",
                "3 dBm",
                "4 dBm",
                "5 dBm",
                "6 dBm",
                "7 dBm",
                "8 dBm",
                "9 dBm",
                "10 dBm (10 mW)",
                "11 dBm",
                "12 dBm",
                "13 dBm",
                "14 dBm",
                "15 dBm",
                "16 dBm",
                "17 dBm",
                "18 dBm",
                "19 dBm",
                "20 dBm",
                "21 dBm (1/8 W)",
                "22 dBm",
                "23 dBm",
                "24 dBm (1/4 W)",
                "25 dBm",
                "26 dBm",
                "27 dBm (1/2 W)",
                "28 dBm",
                "29 dBm",
                "30 dBm (1 W)"
        )

        val POLARITY_OPTIONS = arrayOf(
                "Disable",
                "Up",
                "Down"
        )

        val NUM_RETRIES_OPTIONS = arrayOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8"
        )

        val RADIO_MODE_OPTIONS = arrayOf(
            "Mode 1 (Longest Range)",
            "Mode 2 (Long Range)",
            "Mode 3 (Mid Range)",
            "Mode 4 (Short Range)",
            "Custom"
        )

        val SPREADING_FACTOR = arrayOf(
            "SF7",
            "SF8",
            "SF9",
            "SF10",
            "SF11",
            "SF12"
        )

        val BANDWIDTH = arrayOf(
            "31.25 kHz",
            "62.5 kHz",
            "125 kHz",
            "250 kHz",
            "500 kHz"
        )

        val INDICATIONS = arrayOf(
            "Off",
            "On",
            "Blink 1Hz",
            "Blink 2Hz",
            "Blink 1 Time",
            "Blink 2 Times",
            "Blink 3 Times",
            "Blink 4 Times"
        )

        val QOS = arrayOf(
            "Manual",
            "On Receive",
            "On Transmit",
            "On Both"
        )

        val BUTTON_HOLD_TIME  = arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15")
        val BUTTON_CONFIG = arrayOf(
                "No Action",
                "Send 0's",
                "Send Ack Data"
        )
        val RX_LED = arrayOf(
                "Default",
                "During Rx"
        )

        val TX_LED = arrayOf(
                "Default",
                "During Tx"
        )
    }
}