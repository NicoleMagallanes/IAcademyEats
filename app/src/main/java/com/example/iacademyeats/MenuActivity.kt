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
            "The Coffee Bean & Tea Leaf" -> arrayListOf(
                MenuItem("Caramel Macchiatto", "The Coffee Bean & Tea Leaf", 100.0, R.drawable.cbtl_coffee),
                MenuItem("Pumpkin Spice Latte", "The Coffee Bean & Tea Leaf", 80.0, R.drawable.cbtl_iceblend),
                MenuItem("Golden Monkey Special Tea", "The Coffee Bean & Tea Leaf", 120.0, R.drawable.cbtl_tea),
                MenuItem("Americano", "The Coffee Bean & Tea Leaf", 90.0, R.drawable.cbtl_americano)
            )
            "Potato Corner" -> arrayListOf(
                MenuItem("Cheese", "Potato Corner", 70.0, R.drawable.pocor_cheese),
                MenuItem("Chili BBQ", "Potato Corner", 60.0, R.drawable.pocor_chilibbq),
                MenuItem("BBQ", "Potato Corner", 80.0, R.drawable.pocor_bbq),
                MenuItem("Sour Cream", "Potato Corner", 60.0, R.drawable.pocor_sourcream)
            )
            "Kitchen City" -> arrayListOf(
                MenuItem("Beef", "Kitchen City", 90.0, R.drawable.kc_beef),
                MenuItem("Chicken", "Kitchen City", 80.0, R.drawable.kc_chicken),
                MenuItem("Pork", "Kitchen City", 110.0, R.drawable.kc_pork),
                MenuItem("Salmon", "Kitchen City", 70.0, R.drawable.kc_salmon)
            )
            else -> arrayListOf()
        }
    }


    // ViewHolder for the menu item
    private inner class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.itemDescriptionTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        private val orderButton: Button = itemView.findViewById(R.id.orderButton)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(menuItem: MenuItem) {
            nameTextView.text = menuItem.name
            descriptionTextView.text = menuItem.description
            priceTextView.text = "Price: ₱${menuItem.price}" // Display the price
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
