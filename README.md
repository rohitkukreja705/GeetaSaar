# Geeta Saar

An offline Android app with the complete Bhagavad Gita (all 18 chapters, 701 shlokas) in Sanskrit, with transliteration, Hindi and English translations, plus a curated collection of popular Sanskrit/Hindi mantras and Aartis. Favorite any verse, mantra or Aarti, and search across the Gita in any of the three languages.

## Content sources

- **Bhagavad Gita text** (Sanskrit, transliteration, word-by-word meanings, and translations): compiled from the [gita/gita](https://github.com/gita/gita) public-domain dataset (Unlicense). Hindi translation is Swami Ramsukhdas's commentary; English translation is Swami Sivananda's — both included in that dataset alongside ~20 other scholars' translations, in case you want to swap them later (see `app/src/main/assets/gita_verses.json`, and the raw `verse.json` / `translation.json` files at the source repo for the other authors).
- **Mantras and Aartis**: curated from well-known, centuries-old public texts (Gayatri Mantra and Mahamrityunjaya Mantra cross-checked against Wikipedia). **Please proofread `mantras.json` and `aartis.json` against a trusted printed source before publishing** — sacred text needs to be letter-perfect, and I compiled most of these from general knowledge rather than a single verified source. The Hanuman Chalisa entry intentionally includes only the opening doha for this reason.

## Project structure

- Native Android, Kotlin, View-based UI (no Compose), min SDK 23 / target SDK 34.
- All content is bundled as JSON in `app/src/main/assets/` — the app works fully offline, no network permission requested.
- Favorites are stored locally via SharedPreferences.
- Bottom navigation: **Gita** (chapters → verses → verse detail with Sanskrit/Transliteration/Hindi/English tabs), **Mantras**, **Aartis**, **Favorites**, plus a toolbar **Search** across all verses.

## Building

This repo has no committed Gradle wrapper — the GitHub Actions workflow (`.github/workflows/build.yml`) installs Gradle 8.7 directly via `gradle/actions/setup-gradle@v6` and runs `gradle :app:assembleDebug`, matching the CI setup already confirmed working for other Android projects (`android-actions/setup-android@v4`, `actions/setup-java@v5`, `actions/checkout@v6`).

A fixed `app/debug.keystore` is committed so the debug APK's signature stays stable across CI runs (avoids "app not installed" errors from a mismatched signature when reinstalling over a previous build).

To build locally instead: install Android Studio (which bundles Gradle) or a local Gradle 8.7+ install, then run `gradle :app:assembleDebug` from the project root. The resulting APK lands at `app/build/outputs/apk/debug/app-debug.apk`.

## Ideas for follow-up

- Swap in a different Hindi/English translator pair, or let the user pick their preferred commentator per verse (the source dataset has ~20 authors per language already).
- Add audio recitation per verse/mantra.
- Add a "verse of the day" home-screen widget or notification.
- Expand the mantras/Aartis collection once the current list is proofread.
