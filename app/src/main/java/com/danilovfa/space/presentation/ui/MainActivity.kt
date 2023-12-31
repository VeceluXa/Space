package com.danilovfa.space.presentation.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.danilovfa.space.R
import com.danilovfa.space.databinding.ActivityMainBinding
import com.danilovfa.space.presentation.navigation.Screens.TabContainer
import com.danilovfa.space.presentation.mvp.main.MainPresenter
import com.danilovfa.space.presentation.mvp.main.MainView
import com.danilovfa.space.presentation.notifications.ChargingNotificationWorker
import com.danilovfa.space.presentation.navigation.BackButtonListener
import com.danilovfa.space.utils.Constants.Companion.HOME_TAB_ID
import com.danilovfa.space.utils.Constants.Companion.MAP_TAB_ID
import com.danilovfa.space.presentation.notifications.NotificationManager
import com.danilovfa.space.utils.NetworkStatus
import com.danilovfa.space.presentation.navigation.RouterProvider
import com.danilovfa.space.presentation.ui.dialog.TextDialogFragment
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : MvpAppCompatActivity(), MainView, RouterProvider {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    @Inject
    override lateinit var router: Router

    @Inject
    lateinit var hiltPresenter: MainPresenter

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = hiltPresenter

    private var defaultTabId = HOME_TAB_ID

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : AppNavigator(this, R.id.fragmentContainerView) {
        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatus =
            NetworkStatus(this@MainActivity).checkInternetConnection()

        installSplashScreen().apply {
            setOnExitAnimationListener {
                if (!networkStatus) {
                    TextDialogFragment.display(
                        supportFragmentManager,
                        title = getString(R.string.error_title),
                        body = getString(R.string.error_no_connection)
                    ) {
                        finishAffinity()
                    }
                } else {
                    it.remove()
                }
            }
        }
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.title = ""

        if (networkStatus)
            setup(savedInstanceState)

    }

    private fun setup(savedInstanceState: Bundle?) {
        restoreSavedDefaultTab(savedInstanceState)

        setBottomNavigationListener()
        setOnBackPressedListener()

        NotificationManager(this).setupPermissions()
    }

    private fun restoreSavedDefaultTab(savedInstanceState: Bundle?) {
        val savedDefaultTab = savedInstanceState?.getString(SAVED_DEFAULT_TAB_ID)
        if (savedDefaultTab == HOME_TAB_ID || savedDefaultTab == MAP_TAB_ID)
            defaultTabId = savedDefaultTab

        val tabIdOrNull =
            intent.extras?.getString(ChargingNotificationWorker.EXTRA_TAB_CONTAINER_ID)
        if (tabIdOrNull != null) defaultTabId = tabIdOrNull
    }

    private fun setBottomNavigationListener() {
        binding.bottomNavigationView.apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        selectTab(HOME_TAB_ID)
                        true
                    }

                    R.id.navigation_map -> {
                        selectTab(MAP_TAB_ID)
                        true
                    }

                    else -> false
                }
            }

            selectedItemId = when (defaultTabId) {
                MAP_TAB_ID -> R.id.navigation_map
                else -> R.id.navigation_home
            }
        }
    }

    private fun selectTab(containerId: String) {
        val fm = supportFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fm.findFragmentByTag(containerId)
        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            defaultTabId = containerId
            transaction.add(
                R.id.fragmentContainerView,
                TabContainer(containerId).createFragment(fm.fragmentFactory),
                containerId
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            defaultTabId = containerId
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    private fun setOnBackPressedListener() {
        onBackPressedDispatcher.addCallback {
            val fm = supportFragmentManager
            var fragment: Fragment? = null
            val fragments = fm.fragments
            for (f in fragments) {
                if (f.isVisible) {
                    fragment = f
                    break
                }
            }
            if (fragment != null && fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) {
                return@addCallback
            } else {
                presenter.onBackPressed()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_DEFAULT_TAB_ID, defaultTabId)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val SAVED_DEFAULT_TAB_ID = "SavedDefaultTabId"
    }
}