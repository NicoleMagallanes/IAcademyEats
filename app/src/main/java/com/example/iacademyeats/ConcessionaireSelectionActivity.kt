package com.example.iacademyeats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ConcessionaireSelectionActivity : AppCompatActivity() {

    private lateinit var concessionaire1Button: ImageButton
    private lateinit var concessionaire2Button: ImageButton
    private lateinit var concessionaire3Button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concessionaire_selection)

        concessionaire1Button = findViewById(R.id.concessionaire1Button)
        concessionaire2Button = findViewById(R.id.concessionaire2Button)
        concessionaire3Button = findViewById(R.id.concessionaire3Button)

        concessionaire1Button.setOnClickListener { openMenuActivity("The Coffee Bean & Tea Leaf") }
        concessionaire2Button.setOnClickListener { openMenuActivity("Potato Corner") }
        concessionaire3Button.setOnClickListener { openMenuActivity("Kitchen City") }
    }

    private fun openMenuActivity(concessionaireName: String) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("concessionaireName", concessionaireName)
        startActivity(intent)
    }
}
