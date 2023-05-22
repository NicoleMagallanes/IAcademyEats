package com.example.iacademyeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Random


class MenuActivity : AppCompatActivity() {

    private lateinit var concessionaireName: String
    private lateinit var menuItems: ArrayList<MenuItem>
    private lateinit var randomizerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Get the selected concessionaire information from the intent
        concessionaireName = intent.getStringExtra("concessionaireName") ?: ""
        // Load the menu items for the selected concessionaire (replace with your own logic)
        menuItems = loadMenuItems(concessionaireName)

        // Set the title with the concessionaire name
        title = concessionaireName

        // Initialize and configure the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.menuRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = MenuAdapter()
        recyclerView.adapter = adapter

        // Set the menu items to the adapter
        adapter.setData(menuItems)
        // Initialize the randomizer button
        randomizerButton = findViewById(R.id.randomizerButton)
        randomizerButton.setOnClickListener {
            // Randomly select an item from the menu
            val random = Random()
            val randomIndex = random.nextInt(menuItems.size)
            val selectedItem = menuItems[randomIndex]

            // Start the ResultActivity and pass the selected item
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)

        }
    }

    // Replace this with your own logic to load menu items for the selected concessionaire
    private fun loadMenuItems(concessionaireName: String): ArrayList<MenuItem> {
        // Dummy data for demonstration purposes
        return when (concessionaireName) {
            "Concessionaire 1" -> arrayListOf(
                MenuItem("Item 1", "Description 1", 10.0, R.drawable.cbtl_coffee),
                MenuItem("Item 2", "Description 2", 8.0, R.drawable.cbtl_iceblend),
                MenuItem("Item 3", "Description 3", 12.0, R.drawable.cbtl_tea),
                MenuItem("Item 4", "Description 4", 9.5, R.drawable.cbtl_iceblend)
            )
            "Concessionaire 2" -> arrayListOf(
                MenuItem("Item 5", "Description 5", 7.0, R.drawable.pocor_cheese),
                MenuItem("Item 6", "Description 6", 11.0, R.drawable.pocor_chilibbq),
                MenuItem("Item 7", "Description 7", 10.5, R.drawable.pocor_bbq),
                MenuItem("Item 8", "Description 8", 6.5, R.drawable.pocor_sourcream)
            )
            "Concessionaire 3" -> arrayListOf(
                MenuItem("Item 9", "Description 9", 9.5, R.drawable.kc_beef),
                MenuItem("Item 10", "Description 10", 8.5, R.drawable.kc_chicken),
                MenuItem("Item 11", "Description 11", 11.5, R.drawable.kc_pork),
                MenuItem("Item 12", "Description 12", 7.5, R.drawable.kc_salmon)
            )
            else -> arrayListOf()
        }
    }


    // ViewHolder for the menu item
    private inner class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.itemDescriptionTextView)
        private val orderButton: Button = itemView.findViewById(R.id.orderButton)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(menuItem: MenuItem) {
            nameTextView.text = menuItem.name
            descriptionTextView.text = menuItem.description
            itemImageView.setImageResource(menuItem.imageResId)

            orderButton.setOnClickListener {
                sendOrderEmail(menuItem)
            }
        }
    }


    // Adapter for the menu items
    private inner class MenuAdapter : RecyclerView.Adapter<MenuItemViewHolder>() {
        private var data: List<MenuItem> = ArrayList()

        fun setData(menuItems: List<MenuItem>) {
            data = menuItems
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu, parent, false)
            return MenuItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
            val menuItem = data[position]
            holder.bind(menuItem)
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }

    // Replace this with your own logic to send an email to the concessionaire with the selected food item
    private fun sendOrderEmail(menuItem: MenuItem) {
        // Dummy implementation for demonstration purposes
        val subject = "Order from $concessionaireName"
        val message = "Item: ${menuItem.name}\nPrice: ${menuItem.price}"
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
