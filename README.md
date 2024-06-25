**FlickrApp For Android**

**Architecture**
- Architecture generally follows the guidlines set out in the Android developers docs, seperating Presentation, Domain and Data Layers:
    - Presentation contains UI built in compose for displaying photos and Viewmodels for managing current state
    - Domain layer contains business logic for achieing a specific functionality (Use Cases)
   -  Data layer facilitates access to data sources, eg. Flickr Api
 
**Hilt (Dependancy Injection**
- Hilt has been used to manage dependancies, in this case providing Singleton dependancies for Use Cases / Flickr API that could be used all throughout the app

 **User Interface**
 - Material3 Components have been used as they are they provide an interface Android users are already familiar with, eg the search bar with search suggestions
 - This app has been designed with an adaptive approach in mind, therefore it should function well on a tablet as well as a phone with a 2 panel layout

**Retrofit2**
- Retrofit has been used in the data layer to provide a clear and easilly readable way to do API calls to Flickr

