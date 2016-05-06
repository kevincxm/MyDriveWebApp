# MyDriveWebApp

MyDrive is an online cloud storage application. This is a work done by Piyush Shrivastava and Xiaoming Chen only.

MyDrive has a distributed system backend which runs on 10 VMs running in a cluster. The backend comes in two variants. One is using MyDrive File System (design 1) and the other is using MongoDB with GridFS (design 2). For design 1, we have MDFS as the backend data storage system which runs on 10 VMs. The coordination among the VMs is done by implementing Gossip protocol. Leader election is MDFS is done using Bully algorithm. To install MDFS on your cluster, please clone from https://github.com/pshrvst2/MyDrive. Design 2 uses MongoDB along GridFS to store user's files. The replica instance has been tested with 10 VMs and is highly scalable as per our initial results. Design 2 is more secured than design 1. 

If you have any questions about this project, please contact me <me.piyush89@gmail.com> or Xiaoming <kevincxm@gmail.com>.
