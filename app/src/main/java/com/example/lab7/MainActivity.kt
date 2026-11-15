package com.example.lab7

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var listView: ListView
    private lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        spinner = findViewById(R.id.spinner)
        listView = findViewById(R.id.listView)
        gridView = findViewById(R.id.gridView)

        // 先顯示載入狀態，再以 Coroutine 非同步完成資料準備。
        loadFruitData()
    }

    private fun loadFruitData() {
        spinner.isEnabled = false
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("水果載入中...")
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // launch 啟動 Coroutine；delay 不會阻塞 Android 主執行緒。
        lifecycleScope.launch {
            delay(1000)

            val fruits = createFruitList()
            val countOptions = fruits.indices.map { index -> "${index + 1}個" }

            spinner.adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                countOptions
            ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            gridView.numColumns = 3
            gridView.adapter = MyAdapter(
                context = this@MainActivity,
                data = fruits,
                layout = R.layout.adapter_vertical
            )
            listView.adapter = MyAdapter(
                context = this@MainActivity,
                data = fruits,
                layout = R.layout.adapter_horizontal
            )

            spinner.isEnabled = true
        }
    }

    // 集中建立水果資料，避免 onCreate() 塞入太多細節。
    private fun createFruitList(): List<Item> {
        val fruits = mutableListOf<Item>()
        val imageArray = resources.obtainTypedArray(R.array.image_list)

        try {
            for (index in 0 until imageArray.length()) {
                val imageId = imageArray.getResourceId(index, 0)
                if (imageId != 0) {
                    fruits.add(
                        Item(
                            photo = imageId,
                            name = "水果${index + 1}",
                            price = (10..100).random()
                        )
                    )
                }
            }
        } finally {
            // TypedArray 使用完畢後必須釋放。
            imageArray.recycle()
        }

        return fruits
    }
}
