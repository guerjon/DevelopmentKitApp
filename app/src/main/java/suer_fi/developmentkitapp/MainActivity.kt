package suer_fi.developmentkitapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter


data class Item(val id: Long, val title: String, val url: String)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Called when the user taps the Send button  */
    fun goToDevices(view: View) {
        val intent = Intent(this,Devices::class.java)
        startActivity(intent)
    }

}
