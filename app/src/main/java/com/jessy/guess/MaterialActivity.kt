package com.jessy.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    private val REQUEST_RECORD: Int = 100
    val secretNumber = SecretNumber()
    val TAG :String = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            replay()
        }
        counter.setText(secretNumber.count.toString())
        Log.d(TAG, "onCreate: " + secretNumber.secret)
        getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick :String? = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: " + counter + "/" + nick)
    }

    fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, whitch ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                ed_number.setText("")
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    fun check (view : View) {
        var n = ed_number.text.toString().toInt()
        Log.d(TAG, "ed_number " + n)
        val diff :Int = secretNumber.validate(n)
        var message = getString(R.string.yes_you_got_it)
        if (diff < 0) {
            message = getString(R.string.bigger)
        } else if (diff > 0){
            message = getString(R.string.smaller)
        }
        counter.setText(secretNumber.count.toString())
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult: " + nickname)
                replay()
            }
        }
    }
}