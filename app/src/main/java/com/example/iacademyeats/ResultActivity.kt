package com.example.iacademyeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Get the selected item from the intent
        val selectedItem = intent.getParcelableExtra<MenuItem>("selectedItem")

        // Display the selected item in the result view
        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "Selected Item: ${selectedItem?.name}\nPrice: ${selectedItem?.price}"

        // Handle order button click
        val orderButton: Button = findViewById(R.id.orderButton)
        orderButton.setOnClickListener {
            // Send order email
            sendOrderEmail(selectedItem)
        }
    }

    // Replace this with your own logic to send an email to the concessionaire with the selected food item
    private fun sendOrderEmail(menuItem: MenuItem?) {
        // Dummy implementation for demonstration purposes
        val subject = "Order from ${menuItem?.name}"
        val message = "Item: ${menuItem?.name}\nPrice: ${menuItem?.price}"
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("concessionaire@example.com"))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        try {
            startActivity(Intent.createChooser(emailIntent, "Send order email"))
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send order email", Toast.LENGTH_SHORT).show()
        }
    }
}
