# Compose Basics

A small Android demo of Jetpack Compose fundamentals: an onboarding screen
that dismisses to a scrolling list of 1,000 greeting cards, each with
expandable extra text. Built with Compose and Material 3, with the screen
state held in a ViewModel so it survives rotation and process death.

Originally the Jetpack Compose Basics codelab exercise from February 2024;
v2.0 modernizes the toolchain, moves the UI to edge-to-edge, and hoists the
state out of the composables. See [CHANGELOG.md](CHANGELOG.md) for what
changed and what was deliberately left alone.

This public repository is a squashed export of that v2.0.0 modernization:
a single commit, with the earlier private history intentionally not included.

## Tech stack

- Kotlin 2.2.0, Jetpack Compose, Material 3
- ViewModel + StateFlow + `SavedStateHandle` for the onboarding and
  expanded-card state
- Android Gradle Plugin 8.13.0, Gradle 8.14.3 (via the wrapper), JDK 17
- compile/target SDK 36, minSdk 24
- JUnit 4, Compose UI tests

The application ID is `com.enonymous1.composebasics`.

## How it works

- `MainActivity` enables edge-to-edge, then shows `MyApp` inside a
  `Scaffold`, so the content keeps clear of the status and navigation bars.
- `MyApp` shows the onboarding screen until Continue is tapped, then the
  greetings list. Which screen is showing, and which cards are expanded,
  live in `GreetingsViewModel`, backed by `SavedStateHandle`.
- `Greetings` is a `LazyColumn` with a stable key per name, so only the
  visible cards are composed and expanded state stays attached to the
  right card while scrolling.
- The theme keeps the original custom Navy/Blue palette for the
  non-dynamic-color case, and uses the system's dynamic color on
  Android 12 and above, as in v1.0.

## Build and test

```sh
./gradlew testDebugUnitTest assembleDebug
```

The Compose UI and instrumented tests need an emulator or a connected
device:

```sh
./gradlew connectedDebugAndroidTest
```

Open the project in Android Studio and run `MainActivity` to try it:
Continue past onboarding, scroll the list, expand and collapse a card,
and rotate the device mid-list to confirm the onboarding dismissal and
expanded cards survive.

## Notes

- v1.0's minimum SDK was 21; v2.0 raises it to 24, matching the other
  modernized Android projects in this portfolio. Nothing else about the
  app's behavior was changed on purpose: the same 1,000 names, the same
  strings, the same card animation.
- The build was not verified on the machine that produced this export
  (no JDK or Android SDK there): the build gate is CI plus Android
  Studio on Windows.
