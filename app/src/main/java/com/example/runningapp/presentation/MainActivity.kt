package com.example.runningapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Dao
import com.example.runningapp.R
import com.example.runningapp.data.room.RunDatabase
import com.example.runningapp.data.room.UsersDAO
import com.example.runningapp.data.room.entities.Sprint
import com.example.runningapp.data.room.entities.User
import com.example.runningapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var usersDAO: UsersDAO

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val k = usersDAO.addSprints(listOf(Sprint(),Sprint(),Sprint(),Sprint()))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.d("MYTAG", " ДОБАВИЛ")
            },{
                Log.d("MYTAG", it.message?: "")
            })


        changeFragment(RunFragment.newInstance())
    }

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    private fun setListeners(){
        with(binding){
            bnvMenu.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.item_run -> {
                        changeFragment(RunFragment.newInstance())
                        true
                    }
                    R.id.item_history -> {
                        changeFragment(HistoryFragment.newInstance())
                        true
                    }
                    else -> false
                }
            }
            bnvMenu.setOnNavigationItemReselectedListener {

            }
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).addToBackStack(null).commit()
    }
}