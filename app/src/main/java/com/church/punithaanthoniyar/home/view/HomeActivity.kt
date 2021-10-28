package com.church.punithaanthoniyar.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.base.BaseActivity
import com.church.punithaanthoniyar.bible.view.BibleFragment
import com.church.punithaanthoniyar.customviews.recyclerviewpager.RecyclerViewPager
import com.church.punithaanthoniyar.customviews.scrollingpagerindicator.ScrollingPagerIndicator
import com.church.punithaanthoniyar.language.LanguageDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.*


class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,LanguageDialogFragment.ILanguageListener {

    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var drawerView : DrawerLayout
    override fun initializeDi() {
    }

    override fun setUpLayout() {
        setContentView(R.layout.activity_home)
    }

    override fun setUpViews() {

        openHome()

        bottomNavigationView = findViewById(R.id.navigation)
        drawerView = findViewById(R.id.drawer_layout)

        val mDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this,
            drawerView, R.string.ok, R.string.close) {
            override fun onDrawerClosed(view: View) {
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val moveFactor = drawerView.width * slideOffset
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if (imm != null && imm.isActive) {
                    imm.hideSoftInputFromWindow(drawerView.windowToken, 0)
                }
            }
        }

        drawerView?.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        val navigationView = findViewById<View>(R.id.drawer_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    openHome()
                }
                R.id.action_bible -> {
                    val fragment = BibleFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
                        .commit()
                }
                R.id.action_more -> {
                    openNavigationDrawer()
                }
            }
            true
        }

    }

    fun openHome(){
        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    fun openNavigationDrawer(){
        if (drawerView.isDrawerOpen(GravityCompat.END)) {
            drawerView.closeDrawer(GravityCompat.END)
        }else
            drawerView.openDrawer(GravityCompat.END)
    }

    private fun initViews() {
        val imageList: RecyclerViewPager = findViewById(R.id.viewpager)
        val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        layout.isMeasurementCacheEnabled = false
        imageList.layoutManager = layout

        val imageListAdapter: ImageListAdapter = ImageListAdapter(this, arrayListOf())

        imageList.adapter = imageListAdapter
        val recyclerIndicator: ScrollingPagerIndicator = findViewById(R.id.scroll_indicator)
        recyclerIndicator.attachToRecyclerView(imageList)
        imageList.setHasFixedSize(false)
        imageList.isLongClickable = true
        imageList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, i: Int, i2: Int) {
                val childCount = imageList.getChildCount()
                val width = imageList.getChildAt(0).width
                val padding = (imageList.getWidth() - width) / 2
                for (j in 0 until childCount) {
                    val v = recyclerView.getChildAt(j)
                    var rate = 0f
                    if (v.left <= padding) {
                        rate = if (v.left >= padding - v.width) {
                            (padding - v.left) * 1f / v.width
                        } else {
                            1f
                        }
                        v.scaleY = 1 - rate * 0.1f
                        v.scaleX = 1 - rate * 0.1f
                    } else {
                        if (v.left <= recyclerView.width - padding) {
                            rate = (recyclerView.width - padding - v.left) * 1f / v.width
                        }
                        v.scaleY = 0.9f + rate * 0.1f
                        v.scaleX = 0.9f + rate * 0.1f
                    }
                }
                layout.requestLayout()
            }
        })
        imageList.addOnPageChangedListener { oldPosition, newPosition -> }
        imageList.addOnLayoutChangeListener(View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (imageList.getChildCount() < 3) {
                if (imageList.getChildAt(1) != null) {
                    if (imageList.currentPosition === 0) {
                        val v1 = imageList.getChildAt(1)
                        v1.scaleY = 0.9f
                        v1.scaleX = 0.9f
                    } else {
                        val v1 = imageList.getChildAt(0)
                        v1.scaleY = 0.9f
                        v1.scaleX = 0.9f
                    }
                }
            } else {
                if (imageList.getChildAt(0) != null) {
                    val v0 = imageList.getChildAt(0)
                    v0.scaleY = 0.9f
                    v0.scaleX = 0.9f
                }
                if (imageList.getChildAt(2) != null) {
                    val v2 = imageList.getChildAt(2)
                    v2.scaleY = 0.9f
                    v2.scaleX = 0.9f
                }
            }
        })
    }

    class ImageListAdapter internal constructor(private val context: Context, private val imageList: ArrayList<String>) :
        RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.hom_gallery_image_view, parent, false)
            return MyViewHolder(itemView)
        }

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItemCount(): Int {
            return imageList.size
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.action_language -> {
                openLanguage()
            }

        }
        drawerView.closeDrawer(GravityCompat.END)
        return true
    }

    fun openLanguage(){
        LanguageDialogFragment(this).show(supportFragmentManager, "mydialog")
    }

    override fun onBackPressed() {
        if (drawerView.isDrawerOpen(GravityCompat.START)) {
            drawerView.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private val sharedPrefFile = "languageSharedPref"
    override fun setLanguage(lang: String) {

        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources: Resources = resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Language",lang)
        editor.apply()

    }


}