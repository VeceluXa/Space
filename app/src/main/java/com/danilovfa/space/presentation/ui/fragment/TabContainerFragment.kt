package com.danilovfa.space.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danilovfa.space.R
import com.danilovfa.space.presentation.navigation.LocalNavigatorHolder
import com.danilovfa.space.presentation.navigation.Screens.HomeScreen
import com.danilovfa.space.presentation.navigation.Screens.MapScreen
import com.danilovfa.space.utils.BackButtonListener
import com.danilovfa.space.utils.Constants.Companion.MAP_TAB_ID
import com.danilovfa.space.utils.RouterProvider
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TabContainerFragment : Fragment(), RouterProvider, BackButtonListener {

    private val navigator: Navigator by lazy {
        AppNavigator(requireActivity(), R.id.fragmentTabContainerView, childFragmentManager)
    }

    @Inject
    lateinit var navigatorHolder: LocalNavigatorHolder

    private val containerName: String
        get() = requireArguments().getString(EXTRA_NAME) ?: ""

    private val cicerone: Cicerone<Router>
        get() = navigatorHolder.getCicerone(containerName)

    override val router: Router
        get() = cicerone.router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.fragmentTabContainerView) == null) {
            when (containerName) {
                MAP_TAB_ID -> {
                    cicerone.router.replaceScreen(MapScreen())
                }

                else -> {
                    cicerone.router.replaceScreen(HomeScreen())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    companion object {
        private const val EXTRA_NAME = "tab_container_id"
        fun getNewInstance(name: String?) =
            TabContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_NAME, name)
                }
            }
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentTabContainerView)
        return if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            true
        } else {
            (requireActivity() as RouterProvider).router.exit()
            true
        }
    }
}