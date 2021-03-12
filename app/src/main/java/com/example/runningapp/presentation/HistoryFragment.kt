package com.example.runningapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.runningapp.data.room.UsersDAO
import com.example.runningapp.databinding.FragmentHistoryBinding
import com.example.runningapp.presentation.adapters.HistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    @Inject
    lateinit var usersDao: UsersDAO

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter

    companion object{
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
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
            rv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
        val subscription = usersDao.getSprints()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                adapter.submitList(it.toMutableList())
                Log.d("MYTAG", it.toString())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
