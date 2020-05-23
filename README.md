CAB302 Software Development Group 111 Assignment
================================================

## Electronic Billboard Display and Management System

#### Description

This project demonstrates the structure of billboard display and management
system where it includes
three software applications, all connected via a network. 

One application, henceforth referred to as the Billboard Viewer, is a GUI application that will actually
display the contents, filling the screen. It will connect to the Billboard Server, which acts as the
central control hub for all billboard viewers connected to it. The server will not be a GUI application
and users will not interact with it directly. Instead, users will run a second client program, the
Billboard Control Panel. This will be a GUI application that will connect to the server and allow the
user to carry out various management tasks, such as changing which billboards are shown at which
times. The server will store its information in a MariaDB database. 

Link below shows the basic structure of how the system works.  
![Click Me](https://imgur.com/hmxviLP)

#### Packages
Packages

#### How to run