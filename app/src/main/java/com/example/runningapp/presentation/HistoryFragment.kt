package com.example.runningapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.runningapp.data.room.UsersDAO
import com.example.runningapp.databinding.FragmentRunBinding
import com.example.runningapp.presentation.adapters.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment() {

    @Inject
    lateinit var usersDao: UsersDAO

    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    companion object{
        fun newInstance(): RunFragment {
            return RunFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initializeRecycler()
    }

    private fun initializeRecycler(){
        binding.rvSprints.let { rv->
            adapter = HistoryAdapter().also{
                rv.adapter = it
            }
        }
        usersDao.getSprints().apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe(){
                adapter.submitList(it)
            }
        }
    }

}