# AFTERLIFE

Android application for lost-phone protection and digital legacy management.

## Current milestone: Phase 1 — Engineering foundation

This commit establishes the Android/Gradle foundation and a Clean Architecture package layout. Feature behavior and final UI are intentionally not implemented yet.

## Stack
- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose (dependency prepared for the UI phase)
- AndroidX lifecycle
- Minimum Android API 26; compile/target API 35
- Java 17

## Architecture
The initial app uses a feature-oriented Clean Architecture layout:
- `core/`: shared platform and design-system code
- `domain/`: business models and repository contracts
- `data/`: implementations and local persistence
- `feature/`: presentation code grouped by feature

Planned feature areas:
- trusted contact
- SMS-based lost-phone recovery
- encrypted file vault
- digital legacy

## Build
Open the repository in Android Studio (JDK 17, Android SDK 35) and sync Gradle. The Gradle wrapper is not included in this initial foundation commit; generate it from Android Studio or with a local Gradle installation before command-line builds.

## Security baseline
No SMS receiver, location collection, file access, death verification, or data deletion is enabled in this phase. Those capabilities will be added only in their respective milestones with explicit user consent and platform-compliant permissions.
