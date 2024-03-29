# Cinnox Call SDK Quick Start Guide
Cinnox Call SDK is a software development kit (SDK) that allows you to integrate Cinnox's real-time voice communication features into your Android application. This guide will help you get started with the Cinnox Call SDK by walking you through the installation process and showing you how to make a simple voice call.

<img src="https://github.com/CinnoxGitHub/android_callsdk_sample_app/blob/main/Demo.gif" width="200">

# Installation
1. Add the following code snippet inside the repositories block to the app/build.gradle section:
   
```kotlin
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
This will include the JitPack repository in your project, allowing you to fetch dependencies from it.

2. Add the following code snippet inside the dependencies block:

```kotlin
implementation 'com.github.CinnoxGitHub:android_callsdk:4.3.0.641'
```
This line specifies the dependency on the android_callsdk library from the JitPack repository. 

Sync your project with the Gradle files by clicking on the "Sync Now" button or selecting File > Sync Project with Gradle Files.
Congratulations! You have successfully updated the dependencies using JitPack. The android_callsdk library is now included in your Android project.

# Initialization
Before you can use the Cinnox Call SDK, you need to initialize it by calling the `CinnoxCore.initialize()` method with your app's context and your Cinnox service ID:

```kotlin
val cinnoxCore = CinnoxCore.initialize(context, serviceId)
```

You can get an instance of the `CinnoxCallManager` by calling the `getCallManager()` method on the `CinnoxCore` instance:

```kotlin
val cinnoxCallManager = cinnoxCore.getCallManager()
```

# Making an on-net Call
To make a on-net call using Cinnox Call SDK, you need to create an instance of `CinnoxCallOptions` by `initOnnet()` and pass it to the `makeCall()` method on the `CinnoxCallManager`:

```kotlin
val callOptions = CinnoxCallOptions.initOnnet(toEid)
val result = cinnoxCallManager?.makeCall(callOptions)
val callSession = result.callSession
```

The `toEid` parameter is the ID of the user you want to call.

# Making an off-net Call
To make a off-net call using Cinnox Call SDK, you need to create an instance of `CinnoxCallOptions` by `initOffnet()` and pass it to the `makeCall()` method on the `CinnoxCallManager`:

```kotlin
val callOptions = CinnoxCallOptions.initOffnet(toNumber, cliNumber)
val result = cinnoxCallManager?.makeCall(callOptions)
val callSession = result.callSession
```

The `toNumber` parameter is the phone number of the user you want to call. The `cliNumber` parameter is the caller phone number you want to show to callee.

# Listening for Call State Changes
You can register a `CinnoxCallSessionListener` to receive callbacks when the call session's state changes:

```kotlin
class MyCallSessionListener : CinnoxCallSession.CinnoxCallSessionListener {
    override fun onStateChange(callSession: CinnoxCallSession, newState: State) {
        // Handle state change
    }
}

val callSession = cinnoxCallManager?.getActiveCallSession()
callSession?.registerCallSessionListener(MyCallSessionListener())
```

# Conclusion
This guide provided a quick overview of how to get started with the Cinnox Call SDK. By following the steps outlined here, you should be able to make a simple voice call and receive callbacks when the call state changes.

# API Documentation
## [Api Documentation](https://cinnoxgithub.github.io/android_callsdk_sample_app/)
