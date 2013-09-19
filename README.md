roostrap
========

> Twitter Bootstrap Integrated Skin and User Interface Framework for Spring Roo 

Requirements
------------
- [Spring Roo 1.2.3]
- [Apache Maven 3.x]
- SpringSource Tool Suite (STS) Optional

Getting Started
--------------
1. Clone the project from github or download and extract.


Deploying on Google AppEngine
-------

1. Create a project in the Google AppEngine dashboard.
2. Get an application identifier. (eg: xyz)
3. Change the application identifier `<application/>` in the `src/main/WEB-INF/appengine-web.xml` to the created application identifier.
 - `<application>xyz</application>`
4. Run the following command in your shell.
 - `mvn gae:deploy`
5. Follow the instructions along the Maven plugin.
6. Visit `http://xyz.appspot.com` to view your application running!

Running the local development server with Google AppEngine
----------

1. Run the following command in the project root.
- `mvn gae:run`


Running in Tomcat
----------------

Since RooStrap is configured to use Google AppEngine


Update History
--------

Support or Contact
--
