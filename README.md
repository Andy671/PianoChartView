# Piano Chart View
[![](https://jitpack.io/v/Andy671/PianoChartView.svg)](https://jitpack.io/#Andy671/PianoChartView)
[![](https://img.shields.io/badge/minSDK-15-brightgreen.svg)](https://developer.android.com/training/basics/supporting-devices/platforms.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Introduction
Elementary and clean Android View for displaying piano chord and scale charts in music theory apps for pianists.

## Sample
![](http://i.giphy.com/vuaHfrpkTbwOc.gif)

## Installation

### Step 1
Add the JitPack repository to your build file
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

### Step 2
Add the dependency
```gradle
	dependencies {
		compile 'com.github.Andy671:PianoChartView:0.6.2'
	}
```

# Usage
### Creating from xml:

In your <b>layout.xml</b>
```xml
xmlns:custom="http://schemas.android.com/apk/res-auto"
```

```xml
<!-- Custom arguments are optional - if you don't override them it uses default values -->
 <com.kekstudio.pianochartview.PianoChartView
        android:id="@+id/piano_chart_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
	
       	custom:checkedKeysColor="@android:color/white"
       	custom:lightKeysColor="@color/colorCustomLight"
        custom:darkKeysColor="@color/colorCustomDark"
        custom:size="Small"
        custom:checkedKeys="@array/some_integer_array"/>
```
In your <b>arrays.xml</b>
```xml
<!-- Numbers = keys on keyboard -->
    <integer-array name="some_integer_array">
        <item>5</item>
        <item>7</item>
        <item>11</item>
        <item>1</item>
    </integer-array>
```

In your <b>colors.xml</b>
```xml
    <color name="colorCustomLight">#C8E6C9</color>
    <color name="colorCustomDark">#1B5E20</color>
```

### Editing from code:
```java
 	PianoChartView pianoChartView = (PianoChartView) findViewById(R.id.piano_chart_view_small);
	pianoChartView.setCheckedKeys(new int[]{2, 5, 3, 8, 11, 12});
	pianoChartView.setSize(PianoChartView.Size.Small);
	pianoChartView.setLightKeysColor(Color.parseColor("#CFD8DC"));
	pianoChartView.setDarkKeysColor(Color.parseColor("#607D8B"));
	pianoChartView.setCheckedKeysColor(Color.parseColor("#B2EBF2"));
```

See sample for more info

## XML Attributes
| Attribute        | Type                | Default      |
| -----------------|:-------------------:| :------------|
| lightKeysColor   | color               | Color.WHITE  |
| darkKeysColor    | color               | Color.DKGRAY |
| checkedKeysColor | color               | #03A9F4      |
| checkedKeys      | reference (int[])   | { }          |
| size             | enum [Large, Small] | Large        |


## Public methods
| Type          | Method                          |
|--------------------- |--------------------------------|
| void          | setCheckedKeys(int[] numbers)   |
| void          | setSize(Size size)              |
| void          | setLightKeysColor(int color)    |
| void          | setDarkKeysColor(int color)     |
| void          | setCheckedKeysColor(int color)  |
| int[]         | getCheckedKeys()                |
| Size          | getSize()                       |
| int           | getLightKeysColor()             |
| int           | getDarkKeysColor()              |
| int           | getCheckedKeysColor()           |


## Contribution
- Feel free to fork the repo, make pull requests or fix existing bug
- Feel free to open issues if you find some bug or unexpected behaviour

## Buy me a cup of coffee
> Bitcoin Wallet: 15BuUMAW2jUdStPVkoNPt85P8tJnAy5vD4
