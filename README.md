Checkers [![Build Status](https://travis-ci.org/kscott5/Checkers.svg?branch=master)](https://travis-ci.org/kscott5/Checkers)
========

First Mobile appl written using Android and Eclipse.
The app has undergone many changes. More specifically, seperation of UI, Egnine and Unit Test. There are
additional changes I that could be made. For instance, The engine creates a multiple diminsional array with
64 BoardSquareInfo object. The BoardGridView creates a data adapter with 64 CheckerBoardSquare View. The primary
purpose of the CheckerBoardSquare is drawing the data from the BoardSquareInfo. This is a good area of the 
application for an additional changes where the BoarGridView extends ViewGroup not GridView, and @overrides
the onDraw methods found in CheckersBoardSquare. What this allows is is reduction in the total number of object
instaniated and managed within the JVM. The one concern with approach is rendering and refresh rate on the UI.

-Activity Management (App Lifecycle)  
-Device Resolution  
-MultiThreading UI (process on different/seperate thread)  
-Publishing to Google Play  
-TravisCI integration  
-Data binding  
-Unit Testing  

This app did not focus on a matrix 2D or mulitple dimension array for game board engine.

![alt tag](https://pbs.twimg.com/media/Bt5zt83IAAEuWbc.jpg:large)

## Android headless emulatation
-git clone https://github.com/kscott5/DockerFiles.git
-docker build --tag droid-svr --force-rm --file ![alt tag](https://github.com/kscott5/DockerFiles/Docker.vim.androids) .
-mknod /dev/kvm c 0 0
-chmod 664 /dev/kvm
-chown root:libvirt /dev/kvm
-service libvirtd start
-service virtlogd start
-sdkmanager install 'system-images;android-24;default;armeabi-v7a'
-avdmanager create avd --force --name default --package 'system-images;android-25;google_apis;armeabi-v7a'
-adb start-server
-emulator -avd default -avd-arch armeab-v7a -no-window -qemu -display none -nographic &
-adb devices

