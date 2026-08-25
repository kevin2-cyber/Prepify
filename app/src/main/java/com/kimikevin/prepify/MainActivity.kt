package com.kimikevin.prepify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kimikevin.prepify.ui.theme.PrepifyTheme
import com.revenuecat.purchases.ui.revenuecatui.PaywallDialog
import com.revenuecat.purchases.ui.revenuecatui.PaywallDialogOptions
import com.revenuecat.purchases.ui.revenuecatui.customercenter.CustomerCenter

class MainActivity : ComponentActivity() {

    private val viewModel: PurchasesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrepifyTheme {
                val isPro by viewModel.isPro.collectAsState()
                var showPaywall by remember { mutableStateOf(false) }
                var showCustomerCenter by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SubscriptionStatusScreen(
                        isPro = isPro,
                        onUpgradeClick = { showPaywall = true },
                        onManageClick = { showCustomerCenter = true },
                        modifier = Modifier.padding(innerPadding)
                    )

                    // RevenueCat Paywall Dialog
                    if (showPaywall) {
                        PaywallDialog(
                            PaywallDialogOptions.Builder()
                                .setDismissRequest { showPaywall = false }
                                .build()
                        )
                    }

                    // RevenueCat Customer Center
                    if (showCustomerCenter) {
                        CustomerCenter(
                            onDismiss = { showCustomerCenter = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubscriptionStatusScreen(
    isPro: Boolean,
    onUpgradeClick: () -> Unit,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isPro) "Welcome to Prepify Pro! 🚀" else "Get Prepify Pro",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = if (isPro) "You have full access to all features." else "Unlock premium features and more.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isPro) {
            Button(onClick = onManageClick) {
                Text("Manage Subscription")
            }
        } else {
            Button(onClick = onUpgradeClick) {
                Text("Upgrade to Pro")
            }
        }
    }
}
