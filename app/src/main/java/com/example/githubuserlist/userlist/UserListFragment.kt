package com.example.githubuserlist.userlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserlist.*
import com.example.githubuserlist.databinding.UserListFragmentBinding
import javax.inject.Inject

class UserListFragment : Fragment() {
    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: UserListAdapter

    @Inject
    lateinit var viewModelFactory: UserListViewModel.Factory

    private lateinit var viewModel: UserListViewModel

    private val mayApplication: MyApplication by lazy { (requireActivity().application as MyApplication) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mayApplication.appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initAdapter()
        observeEvents()
    }

    override fun onResume() {
        super.onResume()

        val uri = requireActivity().intent.data
        uri?.let {
            if (uri.toString().startsWith("dagger2.study://android")) {
                val code = uri.getQueryParameter("code") ?: return@let
                viewModel.requestAccessToken(code)
            }
        }

        viewModel.requestUserList(mayApplication.token)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.user_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login_menu -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.oAuthUrl(OAuth.clientId))
                )
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        viewModel.accessToken.observe(viewLifecycleOwner, Observer {
            mayApplication.token = it.access_token
        })
    }

    companion object {
        fun newInstance() = UserListFragment()
    }
}
