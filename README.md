# 🏎️ F1 Hub

A modern Android application built to track Formula 1 race sessions and drivers. This app fetches live data from the OpenF1 API and allows users to build a personalized "Favorites" list of drivers saved directly to their device.

## ✨ Features
* **Live F1 Data:** Consumes the free OpenF1 API to display up-to-date race sessions and driver rosters.
* **Local Data Persistence:** Uses a local Room Database to save and manage your favorite drivers. Data persists between app restarts.
* **Modern UI:** Built entirely with Jetpack Compose, featuring a Scaffold setup, Bottom Navigation Bar, and responsive Material 3 components.

## 🛠️ Tech Stack & Architecture
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose
* **Local Database:** Room
* **Network/API:** Ktor & Gson