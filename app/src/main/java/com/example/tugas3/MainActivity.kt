package com.example.tugas3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tugas3.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private var currentIndex = 0
    private var users: List<User> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Observasi hasil user
        viewModel.users.observe(this) { userList ->
            users = userList
            currentIndex = 0
            if (users.isNotEmpty()) {
                showUser(currentIndex)
            } else {
                binding.txtDetailsData.text = "Data user kosong"
            }
        }

        // Observasi error
        viewModel.error.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        // Observasi harga rata-rata
        viewModel.averagePrice.observe(this) { avg ->
            val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            val hargaFormatted = avg?.let { formatter.format(it) } ?: "-"
            binding.txtRataRata.text = "Harga Rata-rata: $hargaFormatted"
        }

        // Tombol permintaan user
        binding.btnStartRequest.setOnClickListener {
            viewModel.fetchUsers()
        }

        // Tombol Previous
        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showUser(currentIndex)
            } else {
                Toast.makeText(this, "Sudah di data pertama", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Next
        binding.btnNext.setOnClickListener {
            if (currentIndex < users.size - 1) {
                currentIndex++
                showUser(currentIndex)
            } else {
                Toast.makeText(this, "Sudah di data terakhir", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol hitung harga rata-rata
        binding.btnHitungRataRata.setOnClickListener {
            viewModel.fetchProducts()
        }
    }

    private fun showUser(index: Int) {
        val user = users[index]
        val info = "${user.id}: ${user.firstName} ${user.lastName} (${user.university})"
        binding.txtDetailsData.text = info
    }
}
