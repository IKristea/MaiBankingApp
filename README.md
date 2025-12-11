# Banking App

A modern Android banking application built with Jetpack Compose and Material Design 3. Features comprehensive card management, subscription tracking, and a beautiful UI with edge-to-edge display.

## Features

### 🏠 Home Screen
- **Card Pager**: Horizontal swipeable card carousel with page indicators
- **Quick Actions**: Transfer, Top-up, and Bill payment shortcuts
- **Upcoming Payments**: Visual subscription preview with service icons
- **Transactions**: Recent transaction history
- **Service Cards**: Quick access to credit offers and partner services
- **Loans Section**: Promotional card for credit offers

### 💳 Card Management
- **Card Details**: Account balance, credit limit, and card information
- **Card Actions**: Show details, Smart subscriptions, Temporary block
- **Transaction History**: View account statements and banking dates

### 🔄 Subscription Management
- **Subscriptions List**: Complete list of monthly subscriptions with service icons
- **Categories**: Upcoming and active subscriptions with totals
- **Subscription Details**: Payment history, source information, transaction details
- **Manage Flow**: Cancel subscription workflow with confirmation steps
- **Cancellation Feedback**: Reason selection and likelihood to return survey

### 🎨 Design Features
- **Material Design 3**: Modern UI with Material 3 components
- **Edge-to-Edge**: Immersive display with transparent status bars
- **Smooth Animations**: Page transitions and scroll effects
- **Service Icons**: Real brand logos for subscriptions (Spotify, Netflix, YouTube, etc.)
- **Responsive Layout**: Optimized for various screen sizes

## Project Structure

```
BankingApp/
├── app/
│   ├── src/main/java/com/banking/carddetails/
│   │   ├── MainActivity.kt                    # Entry point with edge-to-edge setup
│   │   ├── HomeScreen.kt                      # Main landing page with card pager
│   │   ├── CardDetails.kt                     # Card details page
│   │   ├── SubscriptionsListScreen.kt         # List of all subscriptions
│   │   ├── SubscriptionDetailsScreen.kt       # Individual subscription details
│   │   ├── ManageSubscriptionScreen.kt        # Manage/cancel subscription flow
│   │   ├── ConfirmCancelScreen.kt             # Cancellation confirmation
│   │   ├── CancelReasonScreen.kt              # Cancellation feedback form
│   │   ├── LoadingScreen.kt                   # Loading state screen
│   │   ├── SmartSubscriptionsScreen.kt        # Smart subscription detection
│   │   ├── navigation/
│   │   │   └── Navigation.kt                  # Navigation graph and routes
│   │   ├── models/
│   │   │   └── Subscription.kt                # Data models
│   │   ├── data/
│   │   │   └── MockSubscriptionData.kt        # Mock data source
│   │   └── ui/theme/
│   │       ├── Theme.kt                       # Material 3 theme
│   │       └── Type.kt                        # Typography
│   └── src/main/res/
│       ├── values/
│       │   ├── themes.xml                     # Material 3 light theme
│       │   └── colors.xml                     # Material 3 color palette
│       ├── values-night/
│       │   └── themes.xml                     # Material 3 dark theme
│       └── drawable/
│           ├── spotify.png                    # Service icons
│           ├── netflix.png
│           ├── youtube_logo.png
│           ├── amazon.png
│           ├── icloud.png
│           └── cupcut.png
├── CLAUDE.md                                  # Claude Code guidance
├── BACKEND_INTEGRATION.md                     # API integration guide
├── UPDATE_SUMMARY.md                          # Recent updates documentation
└── .gitignore                                 # Git ignore rules
```

## Tech Stack

- **Language**: Kotlin 2.2.21
- **UI Framework**: Jetpack Compose
- **Compose BOM**: 2025.12.00
- **Design**: Material Design 3
- **Navigation**: Navigation Compose 2.9.6
- **Architecture**: MVVM-ready (currently using Compose state)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36

## Building the Project

### Prerequisites

- **Android Studio**: Ladybug or later (2024.2.1+)
- **JDK**: 17 or higher
- **Android SDK**: API 36
- **Gradle**: 9.0+ (included via wrapper)

### Build Steps

```bash
# Clean the project
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### Running the App

**From Android Studio:**
1. Open the project in Android Studio
2. Let Gradle sync complete
3. Connect an Android device or start an emulator
4. Click `Run > Run 'app'` or press `Shift + F10`

**From Command Line:**
```bash
./gradlew installDebug
```

## Navigation Flow

```
HomeScreen (start)
  ├─> CardDetailsScreen
  │    └─> SubscriptionsListScreen
  │         └─> SubscriptionDetailsScreen
  │              └─> ManageSubscriptionScreen
  │                   └─> ConfirmCancelScreen
  │                        └─> CancelReasonScreen
  │
  └─> SubscriptionsListScreen (via Upcoming Payments)
       └─> (same flow as above)
```

## Key Components

### Screens
- **HomeScreen**: Landing page with card pager, quick actions, and content preview
- **CardDetailsScreen**: Account and card information display
- **SubscriptionsListScreen**: All subscriptions grouped by category
- **SubscriptionDetailsScreen**: Transaction history and subscription info
- **ManageSubscriptionScreen**: Service-specific cancellation flow
- **ConfirmCancelScreen**: Cancellation confirmation dialog
- **CancelReasonScreen**: Feedback collection form

### Data Models
- **Subscription**: Service name, amount, frequency, status
- **SubscriptionDetails**: Extended info with payment history
- **CancellationFeedback**: Reason and likelihood to return
- **PaymentHistoryItem**: Individual payment record

## Theme & Colors

### Material 3 Color Palette
- **Primary**: #00A896 (Teal)
- **Secondary**: #00C896 (Green)
- **Tertiary**: #1E5A6E (Dark Teal)
- **Background**: #F5F5F7 (Light Gray)
- **Surface**: #FFFFFF (White)
- **Error**: #BA1A1A (Red)

### Design System
- Transparent status bar with edge-to-edge display
- Light status bar icons (dark mode aware)
- Rounded corners (8dp, 12dp, 16dp, 24dp)
- Elevation and shadows for depth
- Custom gradient backgrounds

## Backend Integration

The app is ready for backend integration. See `BACKEND_INTEGRATION.md` for:
- API endpoint specifications
- Request/response formats
- Data model mapping
- ViewModel implementation guide
- Authentication setup

### Current Mock Data
All screens use `MockSubscriptionData.kt` for demonstration:
- 9 sample subscriptions (Amazon, Spotify, Netflix, YouTube, etc.)
- 9 predefined cancellation reasons
- Payment history samples

## Customization

### Adding New Subscriptions
1. Add service icon to `res/drawable/`
2. Update `getSubscriptionIcon()` in `SubscriptionsListScreen.kt` and `SubscriptionDetailsScreen.kt`
3. Add subscription to `MockSubscriptionData.subscriptions`

### Modifying Colors
1. Edit `res/values/colors.xml` for Material 3 colors
2. Update theme in `res/values/themes.xml`
3. Compose colors in `ui/theme/Theme.kt`

### Adding New Screens
1. Create screen composable in `com.banking.carddetails`
2. Add route to `navigation/Routes` object
3. Register in `AppNavHost` in `Navigation.kt`

## Development

### Code Style
- Stateless composables with callback parameters
- Navigation callbacks instead of NavController exposure
- Material 3 design system
- Compose best practices
- Edge-to-edge layout with `.statusBarsPadding()`

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumentation tests
./gradlew connectedAndroidTest
```

## Future Enhancements

- [ ] ViewModel integration for state management
- [ ] Repository pattern with Retrofit
- [ ] User authentication and security
- [ ] Biometric authentication
- [ ] Real-time transaction notifications
- [ ] Add/Edit/Delete subscriptions
- [ ] Budget tracking and analytics
- [ ] Export transaction history
- [ ] Multi-card support
- [ ] Offline mode with Room database
- [ ] Dark mode improvements
- [ ] Accessibility enhancements
- [ ] Widget support

## Documentation

- **CLAUDE.md**: Guidance for Claude Code when working in this repository
- **BACKEND_INTEGRATION.md**: API integration specifications and guide
- **UPDATE_SUMMARY.md**: Recent updates and changes

## Resources

### Android Development
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

### Design Resources
- Service icons from official brand assets
- Material Design color system
- Edge-to-edge display guidelines

## License

This project is provided as a demo application for educational purposes.

## Contributing

This is a demonstration project. For production use:
1. Implement proper authentication
2. Integrate with secure backend API
3. Add comprehensive error handling
4. Implement proper state management
5. Add unit and integration tests
6. Follow security best practices for financial apps
