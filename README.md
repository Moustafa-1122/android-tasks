# Task Management App

A modern Android task management application built with Java, featuring user authentication, task list management, and a clean MVVM architecture.

## 📱 Features

- ✅ User Authentication (Login/Logout)
- ✅ Task List Management
- ✅ Navigation Drawer
- ✅ Timer Functionality
- ✅ Swipe to Refresh
- ✅ Session Management
- ✅ Exit Confirmation Dialog

## 🏗️ Architecture: MVVM (Model-View-ViewModel)

This project uses the **MVVM (Model-View-ViewModel)** architecture pattern.

### Why MVVM?

1. **Separation of Concerns**: Business logic is separated from UI code, making the app easier to maintain and test.

2. **Lifecycle Awareness**: ViewModel survives configuration changes (like screen rotation), preventing data loss.

3. **Reactive UI**: LiveData allows the UI to automatically update when data changes, reducing boilerplate code.

4. **Testability**: Business logic in ViewModel can be tested independently from Android framework components.

5. **Scalability**: Easy to add new features without affecting existing code structure.

### Architecture Components

#### 1. **Model** (`model/`)
- Represents the data and business logic
- Example: `Task.java` - POJO class for task data
- Independent of UI and framework

#### 2. **View** (`view/`)
- UI components (Activities & Fragments)
- Observes ViewModel and updates UI accordingly
- Examples:
    - `TasksFragment.java` - displays list of tasks
    - `LoginActivity.java` - handles user login
    - `Main1Activity.java` - main navigation
- Does NOT contain business logic

#### 3. **ViewModel** (`viewmodel/`)
- Acts as a bridge between Model and View
- Holds UI-related data that survives configuration changes
- Example: `TaskViewModel.java` - manages task data and loading states
- Uses LiveData to notify View of data changes

#### 4. **Repository** (`repository/`)
- Single source of truth for data
- Abstracts data sources (API, Database, Cache)
- Example: `TaskRepository.java` - fetches tasks from API
- ViewModel communicates only with Repository, not directly with data sources

#### 5. **Data Sources** (`api/`)
- API services and network clients
- Examples:
    - `ApiService.java` - defines API endpoints
    - `RetrofitClient.java` - configures Retrofit
- Repository uses these to fetch data

### Data Flow
```
View (Fragment) → ViewModel → Repository → Data Source (API)
                    ↓
                LiveData
                    ↓
View (Fragment) observes and updates UI
```

### Key Benefits
- ✅ Clean separation between UI and business logic
- ✅ Easy to mock Repository for testing ViewModel
- ✅ LiveData automatically handles lifecycle, preventing memory leaks
- ✅ Configuration changes (rotation) don't require re-fetching data
- ✅ Easy to switch between API and local database in future

## 🛠️ Technologies Used

- **Language**: Java
- **Architecture**: MVVM
- **Networking**: Retrofit 2.9.0
- **UI Components**:
    - Material Design Components
    - RecyclerView
    - SwipeRefreshLayout
    - Navigation Drawer
- **Lifecycle**: ViewModel, LiveData
- **Data Storage**: SharedPreferences
- **API**: JSONPlaceholder (https://jsonplaceholder.typicode.com/)

## 📋 Requirements

- Android Studio Arctic Fox or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 33 (Android 13)
- Java 8+

## 🚀 Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/task-management-app.git
```

2. Open the project in Android Studio

3. Sync project with Gradle files:
```
File → Sync Project with Gradle Files
```

4. Run the app:
```
Run → Run 'app'
```

## 📱 Screenshots

[Add screenshots here]

## 🔑 Login Credentials

For testing purposes:
- Username: `user1` / Password: `123456`
- Username: `admin` / Password: `admin123`

## 📂 Project Structure
```
com.example.projectwork/
├── model/
│   └── Task.java
├── repository/
│   └── TaskRepository.java
├── viewmodel/
│   └── TaskViewModel.java
├── view/
│   ├── fragments/
│   │   ├── TasksFragment.java
│   │   ├── TimerFragment.java
│   │   ├── ProfileFragment.java
│   │   └── SettingsFragment.java
│   └── activities/
│       ├── SplashActivity.java
│       ├── LoginActivity.java
│       └── Main1Activity.java
├── adapter/
│   └── TasksAdapter.java
└── api/
    ├── ApiService.java
    └── RetrofitClient.java
```

## 🔄 App Flow

1. **Splash Screen** → Checks login status
2. If logged in → **Main Activity** (Tasks List)
3. If not logged in → **Login Activity**
4. After login → **Main Activity** with Navigation Drawer
5. Navigation options: Tasks, Profile, Settings, Timer
6. Logout → Returns to Login Activity

## 🎯 Future Enhancements

- [ ] Add local database (Room)
- [ ] Implement task creation/editing
- [ ] Add task categories
- [ ] Push notifications
- [ ] Dark mode support
- [ ] User profile editing

## 👨‍💻 Author

Moustafa Mariam

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgments

- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) for the free API
- [Material Design](https://material.io/) for design guidelines
- Android Developers documentation
