AndroidDeviceApp
================

A simple Android application displaying information about memory space history, active processes and device configuration.

The app is comprised of three tabs:

1. Memory usage history for both internal and external memory. Data is stored in SQLite database on condition that the user
is runninn the application.

2. A list of the currently active processes in the device. Processes are displayed with their PID, name and memory space usage.

3. General information about the device including: Android and kernel version, device model, free internal and external 
memory space, battery level, device uptime and connectivity state and type(3G, Wi-Fi).
