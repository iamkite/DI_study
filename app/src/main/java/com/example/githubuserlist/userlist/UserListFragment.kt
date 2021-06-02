package com.example.githubuserlist.userlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserlist.EventObserver
import com.example.githubuserlist.databinding.UserListFragmentBinding

class UserListFragment : Fragment() {
    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter: UserListAdapter by lazy {
        UserListAdapter(requireContext())
    }

    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        initAdapter()
        observeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        binding.userListRecyclerview.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.VERTICAL,
            false
        )
        binding.userListRecyclerview.adapter = adapter
    }

    private fun observeEvents() {
        viewModel.userList.observe(viewLifecycleOwner, Observer {
            adapter.list.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.showError.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show()
        })
    }
}
