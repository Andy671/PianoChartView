# Piano Chart View
[![](https://jitpack.io/v/Andy671/PianoChartView.svg)](https://jitpack.io/#Andy671/PianoChartView)
[![](https://img.shields.io/badge/minSDK-15-brightgreen.svg)](https://developer.android.com/training/basics/supporting-devices/platforms.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Introduction
Android Piano Chart View for music theory / music apps 

## Demo

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
	        compile 'com.github.Andy671:PianoChartView:0.6.1'
	}
```

# Usage


See sample for more examples

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


## Buy me a cup of coffee
> Bitcoin Wallet: 15BuUMAW2jUdStPVkoNPt85P8tJnAy5vD4
