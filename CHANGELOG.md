# Changelog

## 2.0.0 — 2026-10-29

Modernization of the original Jetpack Compose Basics codelab exercise
(v1.0, February 2024). The app does the same job — an onboarding screen
that dismisses to a list of 1,000 expandable greeting cards — with the
toolchain, layout, and state handling brought to current standards.

This public repository is a squashed export of that modernization: the
code and tests below are the v2.0.0 tree, in a single commit, without
the earlier private history.

### Changed

- Moved the UI to edge-to-edge: `enableEdgeToEdge()` plus a `Scaffold`
  that applies the system-bar insets, replacing the manual
  `statusBarColor` side effect, whose light-status-bar flag was inverted.
- Hoisted the screen state out of the composables into
  `GreetingsViewModel` (StateFlow + `SavedStateHandle`): the onboarding
  dismissal and the set of expanded cards now survive rotation and
  process death from one place, instead of per-card `rememberSaveable`
  state inside the list.
- Gave the `LazyColumn` a stable key per greeting name, and moved the
  1,000-name list out of a composable default argument into the
  ViewModel, so it is built once instead of on recomposition.
- Removed the dead fallback color schemes and the unused `colors.xml`
  palette, keeping the original custom Navy/Blue palette for the
  non-dynamic-color case. The theme no longer touches the status bar.
- Renamed the application ID and package from
  `com.example.jetpackcomposebasics` to `com.enonymous1.composebasics`,
  and the app name to Compose Basics.
- Toolchain: AGP 8.13.0, Gradle 8.14.3, Kotlin 2.2.0, Java 17,
  compile/target SDK 36, minSdk 24 (was 21), Kotlin DSL build scripts,
  a version catalog (`gradle/libs.versions.toml`), and the Compose
  compiler plugin instead of a pinned compiler-extension version.
- Release builds now use R8 shrinking (`minifyEnabled`,
  `shrinkResources`).
- Removed the tutorial comment noise, the tracked `.idea` directory
  (now ignored), and normalized line endings to LF (`.gitattributes`).
  Fixed `gradlew` to be executable, and moved the branch from `master`
  to `main`.

### Added

- Real tests: the ViewModel (names, onboarding, expand/collapse,
  restore from saved state), deterministic Compose UI tests for the
  onboarding screen and the greetings list (expand, expanded text
  shown/hidden), and a package-name instrumentation test.
- GitHub Actions CI: unit tests plus a debug build on every push and
  pull request to `main`, using `setup-android@v4` with the SDK
  platform and build tools installed explicitly.
- MIT LICENSE, this changelog, and an `.editorconfig`.

### Notes and non-goals

- No new features were added: the same 1,000 names, the same strings,
  and the same card animation as v1.0. No persistence beyond the
  `SavedStateHandle` state was added.
- The full build was not verified on the machine that produced this
  export (no JDK or Android SDK there): the build gate is CI plus
  Android Studio on Windows. On first run, check onboarding →
  Continue → scroll → expand/collapse, rotation mid-list, and dark
  mode with dynamic color on and off.
- The old package name appears only in the rename line above.

## 1.0 — 2024-02-12

Original release: Kotlin 1.9.0, AGP 8.2.2, SDK 34, Compose Material 3
with a pinned compiler extension, Java 8, minSdk 21. Onboarding screen
plus a `LazyColumn` of expandable greeting cards, with only placeholder
tests and no LICENSE file, CI, or version catalog.
