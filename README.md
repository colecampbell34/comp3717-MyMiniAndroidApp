# 📈 Portfolio Tracker

A modern Android application built to track your cryptocurrency and stock assets. This app allows users to log their holdings, persists the data locally, and fetches real-time market prices from the web to calculate the live total value of their portfolio.

Built as a mini-app prototype demonstrating modern Android development best practices.

## Features

* **Local Data Persistence:** Uses a local database to save, read, and delete your portfolio holdings so your data remains intact between sessions.
* **Live Market Data:** Consumes a live REST API to display real-time cryptocurrency prices.
* **Dynamic Calculations:** Cross-references your saved local assets with live API prices to calculate your total portfolio net worth in real-time.
* **Modern UI:** Built entirely with Jetpack Compose, featuring a Scaffold setup with a Bottom Navigation Bar.
* **Pull-to-Refresh:** Manually trigger API re-fetches to get the latest market data.

## Tech Stack & Architecture

This project strictly adheres to **Clean Architecture** principles and **State Hoisting** to ensure separation of concerns between the UI Layer and the Data Layer.

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Declarative UI)
* **Local Database:** Room (SQLite abstraction)
* **Network/API:** Retrofit2 & Gson
* **Navigation:** Jetpack Compose Navigation Component (`NavHost`, `NavController`)
* **Architecture Patterns:** Repository Pattern, Single Source of Truth, Stateless/Stateful Composables, UI Logic State Holders.

## Screens

1. **Portfolio Screen:** Add/remove assets (e.g., BTC, ETH) and view your total calculated portfolio value based on live prices.
2. **Market Screen:** View a live feed of current asset prices fetched from the web.

## API Reference
This app uses theCoinCap API (v2) to fetch real-time asset pricing.

Endpoint used: GET https://api.coincap.io/v2/assets

No API key is required.

## Prerequisites
* Android Studio (Latest version recommended)
* Minimum SDK: API 24 (or as configured in your `build.gradle.kts`)
* An active internet connection (to fetch API data)
#