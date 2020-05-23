# FontUtils

[![](https://jitpack.io/v/ali-sardari/FontUtils.svg)](https://jitpack.io/#ali-sardari/FontUtils)

![alt text](https://github.com/ali-sardari/FontUtils/blob/master/screenshot/Screenshot.jpg "ScreenShot Of Font Samples")

Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
```

Step 2. Add the dependency
```groovy
dependencies {
    implementation 'com.github.ali-sardari:FontUtils:1.0.0'
}
```

Then in your Code, you use it like below.

### Add Fonts

Add your custom fonts to `assets/fonts`. All font definitions are relative to this path.

### Installation

Define your default font in your `Application` class in the # `onCreate()` method.

```kotlin
class MyApp : Application()
{
    override fun onCreate()
    {
        super.onCreate()

//        FontUtils.init(this,"IranYekanBoldFN")
        FontUtils.init(this)
    }
}
```

### Usage

```kotlin
    FontUtils.setView(tvTest)
    FontUtils.setView(tvTest,"FontName") //Without '.ttf'

```


