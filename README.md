
# Exhibition Curator Android Application
# About
Exhibition Curator Android is a Java-based Android application that lets users explore, search, and curate virtual art exhibitions.
Users can search for artworks, browse random artworks, retrieve artwork details, and create, update, and manage exhibitions containing their selected pieces.

# Backend Technology
The app relies on the [Exhibition Curator Backend API](https://github.com/Totsua/exhibition-curator-backend) for all data operations—fetching artwork details, creating/editing/viewing exhibitions, and persisting user favorites.

# Features

- **View Random Artwork** from The MET collection
- **Search & Pagination** through the Art Institute of Chicago API and The Metropolitan Museum of Art Collection API
- **Exhibition CRUD**: create, read, update, and delete exhibitions on the server
- **Add/Remove Artworks** within an exhibition

# Tech Stack
- **Language**: Java 11
- **UI**: Android X
- **Android SDK**:  MIN: 28, TARGET: 35
- **Build System**: Gradle Wrapper


# Dependencies
- RetroFit 2.9
- Glide 4.14


# Getting Started

This is an example of how you may set up the project locally.
To get a local copy up and running follow these simple steps.

1. Clone the repo
   ```sh
   git clone https://github.com/Totsua/exhibition-curator-android
   ```
2. Open in Android Studio
    - Launch Android Studio
    - Select Open an existing project
    - Navigate to the cloned folder
    - Let gradle sync and download all dependencies

3. Run the app *
    - Click Run ▶︎ in the toolbar

**NOTE** *:

The RetroFit BASE_URL is dependent on the localhost server that the backend repo is hosted on.  
The default is 8080.

If you are using a physical device to install the application:
The url will be the ip address of the machine (instead of `10.0.2.2`) running the backend.
- Your device must be connected via USB+tethering.
- Your device's firewall allows incoming connections on port 8080 (or whatever server it is hosted on).



This can be changed in the `RetroFitInstance.class`
```sh
    private static final String BASE_URL = "http://10.0.2.2:8080/api/v1/exhibitioncurator/";
```