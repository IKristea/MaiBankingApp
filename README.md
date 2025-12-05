# README - Banking App

A modern Android banking application built with Jetpack Compose, showcasing a beautiful card details page as the main screen.

## Features

- **Card Details Display**: Shows credit card information with a modern, secure design
- **Account Balance**: Displays available balance, credit limit, and used amount
- **Quick Actions**: Pay Bill, Transfer, and Settings buttons
- **Recent Transactions**: Shows transaction history with amounts and dates
- **Modern UI**: Built with Jetpack Compose using Material Design 3
- **Responsive Design**: Optimized for various screen sizes

## Project Structure

```
BankingApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/banking/carddetails/
│   │       │   ├── MainActivity.kt          # Main activity with UI logic
│   │       │   └── ui/theme/
│   │       │       ├── Theme.kt             # App theme configuration
│   │       │       └── Type.kt              # Typography definitions
│   │       ├── res/
│   │       │   ├── values/
│   │       │   │   ├── strings.xml          # String resources
│   │       │   │   └── themes.xml           # Theme resources
│   │       │   └── drawable/
│   │       │       └── ic_launcher_foreground.xml
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── settings.gradle.kts
├── build.gradle.kts
└── gradle/libs.versions.toml
```

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design**: Material Design 3
- **Minimum SDK**: 24
- **Target SDK**: 34

## Building the Project

### Prerequisites

- Android Studio (Flamingo or later)
- JDK 8 or higher
- Android SDK API 34

### Build Steps

1. Clone or extract the project
2. Open the project in Android Studio
3. Sync Gradle files: `Build > Clean Project`
4. Build the app: `Build > Build Bundle(s) / APK(s) > Build APK(s)`

### Running the App

1. Connect an Android device or start an emulator
2. Click `Run > Run 'app'` or press `Shift + F10`

## Components

### MainActivity
- Entry point of the application
- Sets up Jetpack Compose for the UI
- Implements all composable screens

### Key Composables

- **CardDetailsScreen()**: Main screen container
- **HeaderSection()**: Top header with title
- **CreditCardSection()**: Beautiful credit card display
- **BalanceSection()**: Account balance information
- **QuickActionsSection()**: Action buttons
- **RecentTransactionsSection()**: Transaction list
- **TransactionItem()**: Individual transaction display

## Theme Colors

- **Primary**: #1F77D2 (Blue)
- **Secondary**: #FF6B6B (Red)
- **Tertiary**: #4CAF50 (Green)
- **Background**: #F5F5F5 (Light Gray)

## Customization

You can easily customize the app by:

1. **Change Card Details**: Modify the hardcoded values in `CreditCardSection()`
2. **Update Balance**: Edit the balance amount in `BalanceSection()`
3. **Add Transactions**: Add more `TransactionItem()` calls in `RecentTransactionsSection()`
4. **Modify Colors**: Update colors in `ui/theme/Theme.kt`

## Future Enhancements

- Add state management with ViewModel
- Implement API integration for real card data
- Add authentication screens
- Create card management section
- Add more detailed transaction details page
- Implement dark mode support
- Add animations and transitions

## License

This project is provided as a demo application.

## Support

For questions or issues, please refer to the Android Jetpack Compose documentation:
- https://developer.android.com/jetpack/compose
- https://developer.android.com/guide/topics/ui/look-and-feel/themes
