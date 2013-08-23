Bluetooth Connector
=================

Simple sample project showing a method of initiating a connection to a paired 
Bluetooth device with the A2DP profile. I have an HTC A100 Bluetooth Adapter in
my car, and have always found it irritating to have to manually connect to the
device every time. I knew there had to be a way to do this programmatically, but
it took a lot of searching, and a lot of digging through the source, to find out
how to accomplish it. 

The summary is:

* Get the BluetoothAdapter
* Acquire a Bluetooth A2DP proxy from the adapter
* Find the BluetoothDevice you wish to connect to from the adapter
* Use reflection to invoke the private connect(BluetoothDevice) method


Developed By
============

* Kevin Coppock - <kcoppock87@gmail.com>


License
=======

    Copyright 2013 Kevin Coppock

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.