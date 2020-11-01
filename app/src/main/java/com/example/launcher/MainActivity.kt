package com.example.launcher

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.launcher.databinding.ActivityMainBinding
import com.example.samplelauncher.LauncherAppData
import com.example.samplelauncher.LauncherAppDetails


class MainActivity : AppCompatActivity() {

    private lateinit var appDetailsList: List<LauncherAppData>
    lateinit var mainBinding: ActivityMainBinding
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        init()
    }

    private fun init() {
        initAdapter()
        initListeners()
    }

    private fun initListeners() {

        mainBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun initAdapter() {
        appDetailsList = LauncherAppDetails.getAppDetailsList(this).sortedWith(
            Comparator<LauncherAppData> { o1, o2 ->
                o1?.appName?.compareTo(o2?.appName ?: "", true) ?: 0;
            })

        mainBinding.appList.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = Adapter(this).also {
            it.passAppList(appDetailsList)
        }
        mainBinding.appList.adapter = adapter
    }


    fun filter(text: String?) {
        if (text!!.isNotEmpty()) {
            val applist: MutableList<LauncherAppData> = ArrayList()
            for (d in appDetailsList) {
                if (d.appName.toLowerCase().startsWith(text.toLowerCase())) {
                    applist.add(d)
                }
            }
            adapter.passAppList(applist)
        } else
            adapter.passAppList(appDetailsList)

    }
}
