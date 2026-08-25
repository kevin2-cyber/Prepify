# RevenueCat Integration Walkthrough

I have integrated the RevenueCat SDK into the Prepify app using modern best practices, including Compose-based Paywalls and the Customer Center.

## Changes Made

### 1. Dependency Management
- Added `com.revenuecat.purchases:purchases` and `com.revenuecat.purchases:purchases-ui` (v10.18.1) to `gradle/libs.versions.toml`.
- Integrated these dependencies in `app/build.gradle.kts`.
- Enabled `buildConfig` in `app/build.gradle.kts` for environment-aware logging.

### 2. Initialization
- Created [PrepifyApp.kt](file:///Users/kimikevin/github/Prepify/app/src/main/java/com/kimikevin/prepify/PrepifyApp.kt) to initialize the RevenueCat SDK.
- **Security Best Practice**: The API key is now read from `secret.properties` (which is git-ignored) and exposed via `BuildConfig.REVENUECAT_API_KEY`.
- Registered the custom Application class in [AndroidManifest.xml](file:///Users/kimikevin/github/Prepify/app/src/main/AndroidManifest.xml).
- Configured debug logging for debug builds using `LogLevel.DEBUG`.

### 3. Core Logic
- Created [PurchasesManager.kt](file:///Users/kimikevin/github/Prepify/app/src/main/java/com/kimikevin/prepify/PurchasesManager.kt) as a singleton to encapsulate SDK calls:
    - `checkProEntitlement`: Checks if the user has the `prepify_pro` entitlement.
    - `getCustomerInfo`: Retrieves the latest customer data.
    - `fetchOfferings`: Gets the products configured in the RevenueCat dashboard.
- Created [PurchasesViewModel.kt](file:///Users/kimikevin/github/Prepify/app/src/main/java/com/kimikevin/prepify/PurchasesViewModel.kt) to expose subscription state (`isPro`, `customerInfo`) to the UI using `StateFlow`. It also listens for real-time customer info updates.

### 4. UI Integration
- Updated [MainActivity.kt](file:///Users/kimikevin/github/Prepify/app/src/main/java/com/kimikevin/prepify/MainActivity.kt) to:
    - Display the user's subscription status.
    - Use `PaywallDialog` to present the RevenueCat Paywall UI when the "Upgrade to Pro" button is clicked.
    - Use the `CustomerCenter` composable to allow users to manage their subscriptions.

## How to Configure in RevenueCat Dashboard

To make this implementation work, ensure your RevenueCat dashboard is set up:

1. **secret.properties**: Ensure you have a `secret.properties` file in your root directory with:
   ```properties
   REVENUECAT_API_KEY=your_api_key_here
   ```
2. **Entitlements**: Create an entitlement with ID `prepify_pro`.
2. **Products**: Add your Google Play products (e.g., `lifetime`).
3. **Offerings**: Create an offering (e.g., `default`) and attach your product to it.
4. **Paywalls**: Configure the Paywall design in the RevenueCat dashboard. The `PaywallDialog` in the app will automatically fetch this design.
5. **Customer Center**: Enable and configure the Customer Center in the RevenueCat dashboard.

## Best Practices Followed
- **Single Source of Truth**: `PurchasesViewModel` centralizes the subscription state.
- **Real-time Updates**: Using `UpdatedCustomerInfoListener` ensures the UI updates immediately after a purchase.
- **Modern UI**: Leveraging `purchases-ui` for a native, remotely configurable Paywall and Customer Center.
- **Error Handling**: Basic error handling implemented in `PurchasesManager`.

## Verification
- Run the app and click "Upgrade to Pro" to see the Paywall.
- After a successful purchase (using a test account), the UI will automatically update to "Welcome to Prepify Pro!".
- Click "Manage Subscription" to open the self-service Customer Center.
