package me.gr.turntable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        turntable_view.setValueListener {
            Snackbar.make(turntable_view, it, Snackbar.LENGTH_INDEFINITE).show()
        }
        start_button.setOnClickListener {
            turntable_view.start()
        }
    }
}
