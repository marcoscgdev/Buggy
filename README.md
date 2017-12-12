# Buggy  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)
A very simple crash dialog for Android that allows the user to send a report.

---

## Usage:

### Adding the depencency

Add this to your root *build.gradle* file:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```
compile 'com.github.marcoscgdev:Buggy:1.0.0'
```

### Setting up the dialog

Simply initialize it in your Application class. Thats all!

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Buggy.init(this)
                .withEmail("marcoscgdev@gmail.com") // Your email
                .withSubject("Bug report for " + getResources().getString(R.string.app_name)) // Your subject
                .start();
    }

}
```

#### Custom dialog strings (just override it):

```xml
<string name="buggy_dialog_message">There was an error in the application. Do you want to report it?</string>
```

---
>See the *sample project* to clarify any queries you may have.

---

## License

```
Copyright 2017 Marcos Calvo García

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
