# FIND Indoor Positioning System SDK for Android

[![Release](https://jitpack.io/v/kiwiandroiddev/find-client-android.svg)]
(https://jitpack.io/#kiwiandroiddev/find-client-android/)

Usage
-----

First create a FindClient with your server config:
```java
FindClient findClient = new FindClient.Builder(this)
            .baseUrl("http://192.168.1.2:8003")
            .group("your_group")
            .username("your_username")
            .build();
```

Ensure you have the [required permissions](#permissions), then get the device location with:
```java
findClient.track()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
           @Override
           public void accept(String location) {
               Timber.d("Location: " + location);
           }
        });
```
That's all you'll need to add indoor-location support to your app.

If you also want to be able to train your FIND server with new locations from your app, use the `learn()` method:
```java
findClient.learn("living-room")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onComplete() {
                Timber.d("WiFi fingerprint submitted!");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("onError: " + e.toString(), e);
            }
        });
```

Required Permissions<a name="permissions" />
--------------------------------------------

For the FindClient to work, the user must have enabled **Location Services** and granted the `ACCESS_FINE_LOCATION` permission to your app.

`ACCESS_FINE_LOCATION` must be granted by the user at runtime on Android 6.0 and above. To do this, see the [official documentation](https://developer.android.com/training/permissions/requesting.html) on runtime permissions and/or check out the [Dexter](https://github.com/Karumi/Dexter) library.

Refer to the included sample app for an example.

Download
--------

**Step 1.**

Add the JitPack repository to your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2.**

Add the dependency to your app's `build.gradle`

```groovy
dependencies {
    compile 'com.github.kiwiandroiddev:find-client-android:v0.1-alpha'
}
```

About
-----

This SDK and sample was forked from the official [Find client for Android](https://github.com/uncleashi/find-client-android)

About FIND
----------

The **Framework for Internal Navigation and Discovery (FIND)** allows you to use your (Android) smartphone or WiFi-enabled computer (laptop or Raspberry Pi or etc.) to determine your position within your home or office. You can easily use this system in place of motion sensors as its resolution will allow your phone to distinguish whether you are in the living room, the kitchen or the bedroom, etc. The position information can then be used in a variety of ways including home automation, way-finding, or tracking!
To learn more about it or to run your own private server, check out https://github.com/schollz/find

Authors
-------

 - Akshay Dekate (sample app)
 - Shailesh Srivastava (sample app)
 - Matthew Clarke (SDK)

License
-------

The code supplied here is covered under the MIT Open Source License:

Copyright (c) 2016 Akshay Dekate

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
