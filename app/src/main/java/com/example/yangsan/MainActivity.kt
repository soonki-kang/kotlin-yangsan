package com.example.yangsan
//https://github.com/ahmed-guedmioui/Room_Databese_tutorail/tree/main/app

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.yangsan.components.rememberSkDropdownMenuStateHolder
import com.example.yangsan.db.RsrvDatabase
import com.example.yangsan.presentation.AddScdlScreen
import com.example.yangsan.presentation.AddUserScreen
import com.example.yangsan.presentation.ScdlsScreen
import com.example.yangsan.presentation.ScdlsViewModel
import com.example.yangsan.presentation.UsersViewModel
import com.example.yangsan.ui.theme.YangSanTheme
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            RsrvDatabase::class.java,
            "Rsrv.DB"
        ).build()
    }

    private val scdlViewModel by viewModels<ScdlsViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass:Class<T>): T
                {
                    return ScdlsViewModel(database.dao) as T
                }
            }
        }
    )

    private val userViewModel by viewModels<UsersViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass:Class<T>): T
                {
                    return UsersViewModel(database.userDao) as T
                }
            }
        }
    )


    companion object {
        @SuppressLint("NewApi")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val decFormatter = DecimalFormat("##")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YangSanTheme {
                val context = LocalContext.current.resources

                val scdlTypeList: List<String> =
                    context.getStringArray(R.array.scdlType).toList()
                val scdlTypeHolder = rememberSkDropdownMenuStateHolder(itemList = scdlTypeList)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by scdlViewModel.state.collectAsState()
                    val userState by userViewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "ScdlsScreen") {
                        composable("ScdlsScreen") {
                            ScdlsScreen(
                                state = state,
                                navController = navController,
                                onEvent = scdlViewModel::onEvent,
                                scdlTypeHolder = scdlTypeHolder
                            )
                        }
                         composable("AddScdlScreen") {
                            AddScdlScreen(
                                state = state,
                                navController = navController,
                                onEvent = scdlViewModel::onEvent,
                                scdlTypeHolder = scdlTypeHolder,
                            )
                        }
                         composable("AddUserScreen") {
                            AddUserScreen(
                                state = userState,
                                navController = navController,
                                onEvent = userViewModel::onEvent,
                            )
                        }
                    }
                }
            }
        }
    }
}


