package com.headshot.discountforme.Main.Activities.Home.View

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.headshot.discountforme.Authentication.Login.View.LoginActivity
import com.headshot.discountforme.Main.Activities.FilterAndSearch.View.FilterAndSearchActivity
import com.headshot.discountforme.Main.Activities.Home.InAppUpdate
import com.headshot.discountforme.Main.Activities.Home.ViewModel.HomeViewModel
import com.headshot.discountforme.Main.Adapters.BrandsAdapter
import com.headshot.discountforme.Main.Adapters.CategoriesAdapter
import com.headshot.discountforme.Main.Adapters.HomeAdapter
import com.headshot.discountforme.R
import com.headshot.discountforme.Utils.Constants
import com.headshot.discountforme.Utils.MenuListFragment.View.MenuListFragment
import com.headshot.discountforme.Utils.ParentClass
import com.headshot.discountforme.databinding.ActivityHomeBinding
import com.headshot.discountforme.databinding.HomeNavBinding
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import com.mxn.soul.flowingdrawer_core.ElasticDrawer.OnDrawerStateChangeListener
import com.mxn.soul.flowingdrawer_core.FlowingDrawer
import kotlinx.android.synthetic.main.home_nav.*
import spencerstudios.com.bungeelib.Bungee
import java.util.*
private const val UPDATE_REQUEST_CODE = 123

class HomeActivity : ParentClass() {
    var homeNavBinding: HomeNavBinding? = null
    var mDrawer: FlowingDrawer? = null
    var homeViewModel: HomeViewModel? = null
    var categoriesAdapter: CategoriesAdapter? = null
    var homeAdapter: HomeAdapter? = null
    var popFilter: BottomSheetDialog? = null
    var rvCategories: RecyclerView? = null
    var relativeHighestSale: RelativeLayout? = null
    var tvHighestSale: TextView? = null
    var rlAToZ: RelativeLayout? = null
    var tvAToZ: TextView? = null
    var rlZToA: RelativeLayout? = null
    var tvZToA: TextView? = null
    var tvSave: TextView? = null
    var brandsAdapter: BrandsAdapter? = null
    var isLogin: Boolean? = null
    var dialogLogin: Dialog? = null
    private lateinit var inAppUpdate: InAppUpdate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeNavBinding = HomeNavBinding.inflate(layoutInflater)
        val view: View = homeNavBinding!!.root
        setContentView(view)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("HOSSAM")


        inAppUpdate = InAppUpdate(this)
        Log.e("lang", getLang(this))

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.e("token", "Refreshed token: $token")
            val sharedPreferences = getSharedPreferences(Constants.mobileToken, MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.putString("m_token", token)
            edit.apply()
            // Log and toast

        })
        if (getLang(this) == "ar") {
            homeNavBinding!!.drawerlayout.visibility = View.VISIBLE
            homeNavBinding!!.drawerlayout2.visibility = View.GONE
            mDrawer = homeNavBinding!!.drawerlayout
            setupMenu()
        }
        if (getLang(this) == "en") {
            homeNavBinding!!.drawerlayout2.visibility = View.VISIBLE
            homeNavBinding!!.drawerlayout.visibility = View.GONE
            mDrawer = homeNavBinding!!.drawerlayout2
            setupMenu2()
        }

        Log.e("rf", homeNavBinding!!.drawerlayout2.visibility.toString() + "GOODD")
        Log.e("rfff", homeNavBinding!!.drawerlayout.visibility.toString() + "GOODD")

//        setupMenu();
        initUi()
        handleDirections()
        initEventDriven()
        //checkUpdate()

    }

    private fun initEventDriven() {
        Log.e("token", sharedPrefManager.userDate.token + "GOOD")
        activityHomeBinding().ivMenu.setOnClickListener { v -> mDrawer!!.openMenu() }
        activityHomeBinding().ivFilter.setOnClickListener { v -> popFilter!!.show() }
        activityHomeBinding().ivSearch.setOnClickListener { v ->
            if (TextUtils.isEmpty(activityHomeBinding().etSearch.text.toString())) {
                activityHomeBinding().etSearch.error = getString(R.string.search)
            } else {
                val intent = Intent(this@HomeActivity, FilterAndSearchActivity::class.java)
                intent.putExtra("type_go", "search")
                intent.putExtra("search", activityHomeBinding().etSearch.text.toString())
                startActivity(intent)
                Bungee.split(this@HomeActivity)
            }
        }
        activityHomeBinding().etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
           // if (actionId == EditorInfo.IMEFirebaseMessaging_ACTION_SEARCH) {
                if (TextUtils.isEmpty(activityHomeBinding().etSearch.text.toString())) {
                    activityHomeBinding().etSearch.error = ""
                } else {
                    val intent = Intent(this@HomeActivity, FilterAndSearchActivity::class.java)
                    intent.putExtra("type_go", "search")
                    intent.putExtra("search", activityHomeBinding().etSearch.text.toString())
                    startActivity(intent)
                    Bungee.split(this@HomeActivity)
                }
                return@OnEditorActionListener true
        //    }
            false
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate!!.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleDirections() {
        if (getLang(this) == "ar") {
            activityHomeBinding().etSearch.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        } else {
            activityHomeBinding().etSearch.gravity = Gravity.END or Gravity.CENTER_VERTICAL
        }
    }

    private fun initUi() {
        dismiss_keyboard()
        initDialog()
        isLogin = sharedPrefManager.loginStatus
        homeViewModel = ViewModelProvider(this@HomeActivity).get(HomeViewModel::class.java)
        homeViewModel!!.getCategories()
        homeViewModel!!.categoriesList.observe(this@HomeActivity,
            { data ->
                if (data != null) {
                    activityHomeBinding().rvCategories.hideShimmerAdapter()
                    categoriesAdapter!!.addAll(homeViewModel!!.categoriesList.value)
                    categoriesAdapter!!.notifyDataSetChanged()
                    if (data.size > 0) {
                        activityHomeBinding().rvCategories.visibility = View.VISIBLE
                    } else {
                        activityHomeBinding().rvCategories.visibility = View.GONE
                    }
                } else {
                    errorToast(this@HomeActivity, getString(R.string.somethingWentWrong))
                    activityHomeBinding().rvCategories.visibility = View.GONE
                }
            })
        homeViewModel!!.getHome(
            "0",
            Constants.beforApiToken + sharedPrefManager.userDate.token,
            isLogin
        )
        homeViewModel!!.itemPagedList.observe(this@HomeActivity,
            { listBeans ->
                activityHomeBinding().rvCoupons.hideShimmerAdapter()
                homeAdapter!!.submitList(listBeans)
                Log.v("size", listBeans!!.size.toString() + "GOOD")
            })
        //    RecyclerView rvCategories;
//    RelativeLayout relativeHighestSale;
//    TextView tvHighestSale;
//    RelativeLayout rlAToZ;
//    TextView tvAToZ;
//    RelativeLayout rlZToA;
//    TextView tvZToA;
        popFilter = BottomSheetDialog(this@HomeActivity, R.style.AppBottomSheetDialogTheme)
        popFilter!!.setContentView(R.layout.popup_filter)
        rvCategories = popFilter!!.findViewById<View>(R.id.rvCategories) as RecyclerView?
        relativeHighestSale =
            popFilter!!.findViewById<View>(R.id.relativeHighestSale) as RelativeLayout?
        tvHighestSale = popFilter!!.findViewById<View>(R.id.tvHighestSale) as TextView?
        rlAToZ = popFilter!!.findViewById<View>(R.id.rlAToZ) as RelativeLayout?
        tvAToZ = popFilter!!.findViewById<View>(R.id.tvAToZ) as TextView?
        rlZToA = popFilter!!.findViewById<View>(R.id.rlZToA) as RelativeLayout?
        tvZToA = popFilter!!.findViewById<View>(R.id.tvZToA) as TextView?
        tvSave = popFilter!!.findViewById<View>(R.id.tvSave) as TextView?
        homeViewModel!!.getBrands()
        homeViewModel!!.brandsList.observe(this@HomeActivity,
            { data ->
                if (data != null) {
                    brandsAdapter!!.addAll(homeViewModel!!.brandsList.value)
                    brandsAdapter!!.notifyDataSetChanged()
                    if (data.size > 0) {
                        rvCategories!!.visibility = View.VISIBLE
                    } else {
                        rvCategories!!.visibility = View.GONE
                    }
                } else {
                    errorToast(this@HomeActivity, getString(R.string.somethingWentWrong))
                    rvCategories!!.visibility = View.GONE
                }
            })
        initRecycler()
        mDrawer!!.setOnDrawerStateChangeListener(object : OnDrawerStateChangeListener {
            override fun onDrawerStateChange(oldState: Int, newState: Int) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED")
                }
            }

            override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {
                Log.i(
                    "MainActivity",
                    "openRatio=$openRatio ,offsetPixels=$offsetPixels"
                )
            }
        })
        if (mDrawer!!.isMenuVisible) {
            mDrawer!!.closeMenu()
        }
    }

    fun setupMenu() {
        val fm = supportFragmentManager
        var mMenuFragment = fm.findFragmentById(R.id.id_container_menu) as MenuListFragment?
        if (mMenuFragment == null) {
            mMenuFragment = MenuListFragment()
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit()
        }
    }

    fun setupMenu2() {
        val fm = supportFragmentManager
        var mMenuFragment = fm.findFragmentById(R.id.id_container_menu2) as MenuListFragment?
        if (mMenuFragment == null) {
            mMenuFragment = MenuListFragment()
            fm.beginTransaction().add(R.id.id_container_menu2, mMenuFragment).commit()
        }
    }

    override fun onBackPressed() {
        if (mDrawer!!.isMenuVisible) {
            mDrawer!!.closeMenu()
        } else {
            super.onBackPressed()
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate?.onDestroy()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }

    private fun setLocale() {
        val locale = Locale(getLang(this@HomeActivity))
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate!!.onResume()

        if (mDrawer!!.isMenuVisible) {
            mDrawer!!.closeMenu()
        }
    }

    fun activityHomeBinding(): ActivityHomeBinding {
        return if (getLang(this) == "ar") {
            homeNavBinding!!.home
        } else {
            homeNavBinding!!.home2
        }
    }

    private fun initRecycler() {
        homeAdapter = HomeAdapter(
            this@HomeActivity, homeViewModel,
            Constants.beforApiToken + sharedPrefManager.userDate.token, isLogin, dialogLogin
        )
        val linearLayoutManager1 =
            LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
        activityHomeBinding().rvCoupons.layoutManager = linearLayoutManager1
        activityHomeBinding().rvCoupons.adapter = homeAdapter
        brandsAdapter = BrandsAdapter(
            this@HomeActivity,
            Constants.beforApiToken +
                    sharedPrefManager.userDate.token,
            tvSave,
            relativeHighestSale,
            tvHighestSale,
            rlAToZ,
            tvAToZ,
            rlZToA,
            tvZToA
        )
        val linearLayoutManager2 =
            LinearLayoutManager(this@HomeActivity, RecyclerView.HORIZONTAL, false)
        rvCategories!!.layoutManager = linearLayoutManager2
        rvCategories!!.adapter = brandsAdapter
        categoriesAdapter = CategoriesAdapter(
            this@HomeActivity,
            activityHomeBinding().rvCategories,
            homeViewModel,
            Constants.beforApiToken + sharedPrefManager.userDate.token,
            activityHomeBinding().relativeAll,
            activityHomeBinding().rvCoupons,
            homeAdapter,
            isLogin
        )
        val linearLayoutManager =
            LinearLayoutManager(this@HomeActivity, RecyclerView.HORIZONTAL, false)
        activityHomeBinding().rvCategories.layoutManager = linearLayoutManager
        activityHomeBinding().rvCategories.adapter = categoriesAdapter
    }

    override fun initDialog() {
        dialogLogin = Dialog(this@HomeActivity)
        dialogLogin!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLogin!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialogLogin!!.setContentView(R.layout.pop_up_login)
        val lp1 = WindowManager.LayoutParams()
        lp1.copyFrom(dialogLogin!!.window!!.attributes)
        lp1.width = WindowManager.LayoutParams.MATCH_PARENT
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT

    }
}