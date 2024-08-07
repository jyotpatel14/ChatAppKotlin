package com.example.chatappkotlin

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        val mainView = findViewById<View>(R.id.auth_main)
        notchBarSetup(mainView)

        setUpViewPager()

    }

    private fun setUpViewPager(){
        val tabLayout : TabLayout =findViewById(R.id.main_tab_layout)
        val viewPager : ViewPager2 = findViewById(R.id.main_view_pager)

        val vpAdapter = MainVPAdapter(supportFragmentManager, lifecycle)
        vpAdapter.addFragment(SignupFragment(),"Sign Up")
        vpAdapter.addFragment(LoginFragment(),"Login")


        viewPager.adapter = vpAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = vpAdapter.getPageTitle(position)
        }.attach()
    }

    private fun notchBarSetup(mainView : View){
        ViewCompat.setOnApplyWindowInsetsListener(mainView){view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBarsInsets.top
            }
            // Return the insets so they can be consumed by other views
            insets
        }
    }
}