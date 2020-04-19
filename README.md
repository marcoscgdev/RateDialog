# RateDialog  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=9)
An android library to display a rate dialog in an easy way.

---

## Releases:

#### Current release: 1.0.7.

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
- Italian
- Dutch
- Portuguese
- Brazilian
- Russian
- Polish
- Turkish

Translate this library into your language [here](https://goo.gl/CFZzTh).

---

## Usage:

### Adding the depencency

Add this to your root *build.gradle* file:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Now add the dependency to your app build.gradle file:

```groovy
implementation 'com.github.marcoscgdev:RateDialog:1.0.7'
```

### Creating the dialog

Show the dialog each 3 days and each 7 launches (default config):

```java
RateDialog.init(this);
```

Shows the dialog with custom config:

```java
RateDialog.init(this, 2, 0); // daysUntilPrompt, launchesUntilPrompt
```

You can also get the default config values:

```java
RateDialog.DEFAULT_DAYS_UNTIL_PROMPT
RateDialog.DEFAULT_LAUNCHES_UNTIL_PROMPT
```

Show the dialog instantly:

```java
RateDialog.showDialog(this);
```

### Creating custom dialog instance

```java
RateDialog rateDialog = new RateDialog(this, "custom_rate_dialog_key");
rateDialog.init();
```

You can also use custom config

```java
RateDialog rateDialog = new RateDialog(this, "custom_rate_dialog_key", daysUntilPrompt, launchesUntilPrompt);
```

Show the custom dialog instantly:

```java
RateDialog rateDialog = new RateDialog(this, "custom_rate_dialog_key");
rateDialog.showDialog();
```

**NOTE:** Use a different dialog key for each dialog instance.

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
Copyright 2020 Marcos Calvo García

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

## Support on Beerpay
Hey dude! Help me out for a couple of :beers:!

[![Beerpay](https://beerpay.io/marcoscgdev/RateDialog/badge.svg?style=beer-square)](https://beerpay.io/marcoscgdev/RateDialog)  [![Beerpay](https://beerpay.io/marcoscgdev/RateDialog/make-wish.svg?style=flat-square)](https://beerpay.io/marcoscgdev/RateDialog?focus=wish)
