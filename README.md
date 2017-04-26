# RateDialog  [![API](https://img.shields.io/badge/API-9%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=9)
An android library to display a rate dialog in an easy way.

---

## Releases:

#### Current release: 1.0.1.

You can see all the library releases [here](https://github.com/marcoscgdev/RateDialog/releases).

---

## Screenshots

<img src="https://raw.githubusercontent.com/marcoscgdev/RateDialog/master/device-2017-04-14-140649.png" width="350">

---

## Features

- Roboto font
- Multilanguage
- Easy configuration
- Fully material designed

### Language

RateDialog currently supports the following languages:

- English (default)
- Spanish
- German
- French
- Portuguese

More languages will be added.
It would be great if you could help me with that. You can send me a translation request via email: *marcoscgdev@gmail.com*

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
compile 'com.github.marcoscgdev:RateDialog:1.0.1'
```

### Creating the dialog

Show the dialog each 3 days and each 7 launches (default config):

```
RateDialog.with(this);
```

Shows the dialog with custom config. Use 0 to get the default value:

```
RateDialog.with(this, 2, 0); // daysUntilPrompt, launchesUntilPrompt
```

Show the dialog instantly:

```
RateDialog.show(this);
```

Custom dialog strings (just override it):

```
<string name="rate_dialog_title">Rate %s.</string>
<string name="rate_dialog_message">If you enjoy using %s, please help us by rating it. It only takes a few seconds!</string>
<string name="rate_dialog_action_rate">Rate</string>
<string name="rate_dialog_action_never">No, thanks</string>
<string name="rate_dialog_action_later">Later</string>
<string name="rate_dialog_thank_you">Thank you!</string>
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
