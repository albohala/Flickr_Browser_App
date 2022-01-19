package com.example.flickrbrowserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickrbrowserapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var rvAdapter: RVAdapter
    private lateinit var photos: ArrayList<PhotosDetails>

    private val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    val apiKey = "c89d39918420427d0527b6085ca8bf4c"
    val secret = "169dd517b15de913"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photos = arrayListOf()

        rvAdapter = RVAdapter(photos, this)
        binding.rvMain.adapter = rvAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            val etSearch = binding.etSearch.text.toString()
            var url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$apiKey&format=json&&nojsoncallback=1&extras=url_h,tags&text=$etSearch"
            displayData(etSearch)
            binding.etSearch.text.clear()
        }
    }

    private fun displayData(search: String) {
        apiInterface?.getData("?method=flickr.photos.search&api_key=$apiKey&format=json&nojsoncallback=1&extras=url_h,tags&text=$search")?.enqueue(object:
            Callback<PhotosItem> {
            override fun onResponse(call: Call<PhotosItem>, response: Response<PhotosItem>) {
                try {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for (uni in responseBody.photos.photo) {
                            photos.add(uni)
                            rvAdapter.update(photos)
                        }
                        rvAdapter.update(photos)
                        Log.d("ADD_UNI", "onResponse: $photos")
                    }
                } catch (e: Exception) {
                    Log.d("DISPLAY_DATA", "onResponse: Did not display data")
                    Log.d("EXCEPTION", "onResponse: $e")
                }
            }

            override fun onFailure(call: Call<PhotosItem>, t: Throwable) {
                Log.d("FAILED", "onResponse: Did not display data")
            }
        })
    }
}