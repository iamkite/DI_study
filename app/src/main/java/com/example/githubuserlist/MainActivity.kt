package com.example.githubuserlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuserlist.databinding.ActivityMainBinding
import com.example.githubuserlist.userdetail.UserListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.fragment_container) as UserListFragment?
            ?: supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserListFragment.newInstance())
                .commit()
    }
}
