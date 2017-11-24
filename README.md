# Wifi Indoor Positioning [![Build Status](https://travis-ci.org/Talentica/WifiIndoorPositioning.svg?branch=master)](https://travis-ci.org/Talentica/WifiIndoorPositioning) [![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) [![GitHub version](https://badge.fury.io/gh/Talentica%2FWifiIndoorPositioning.svg)](https://badge.fury.io/gh/Talentica%2FWifiIndoorPositioning)

<img src="/media/image-WiFi.jpg" align="middle" />&nbsp;

This app uses Fingerprinting method for evaluating current location of the device relative to Access Points (for e.g. Wifi Routers). Fingerprinting is a great technique for positioning. It uses Received Signal Strength Index (RSSI) measurements. The basic idea of the fingerprinting method is to match a database to a particular fingerprint in the area at hand. When used with Wi-Fi systems, the fingerprinting method can be typically divided into two phases, calibration phase and positioning phase. As shown in Figure, the calibration phase is for establishing a database storing locations of Reference Points (RPs) in the area of interest. In this phase, signal strengths(RSS) from all the RPs are measured first, then the mean values of the RSS at each of the RPs are calculated, along with other information including the coordinates, the orientation and MAC address etc. In the positioning phase, the signal strengths from the APs are measured at the mobile side and compared with all the records in the database to identify the most probable location of the mobile object using either the deterministic or probabilistic algorithms.

<img src="/media/details.jpg" />&nbsp;

#### This App requires the Wi-Fi and location services to store the RSS values of the access points and reference points.

The functionalities offered :octocat: :star2: are:


#### 1. Adding an Access Point with supported Algorithm types

<img src="/media/AlgoAndCreateAccessPoint.gif" />&nbsp;

#### 2. Adding a Reference Point

<img src="/media/AddReferencePoint.gif" />&nbsp;

#### 3. User's Location or Device's Location

<img src="/media/EvaluateTheLocation.gif" />&nbsp;

## Minimum Requirements

 * Android Studio 3.0.1
 * Android SDK Platform 26 (android-O)
 * Android sdk tools 26.0.2
 * Android sdk build-tools 26.0.2

