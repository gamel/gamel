GAMEL
=====
GAMEL is an extension written in Scala to facilitate text adventure game making. 

The gamel Scala extention provides useful abstractions of game code to users.

Currently under development.

Object (Proposed)
-----------------
+ entity
+ scene
+ action
+ game

Syntax (Proposed)
-----------------
The syntax of GAMEL is currently modeled after SQL.
    
  ```
  create entity as <entity_type> (
      use name <entity_name>
      use description <description>
      has actions <action_name>
      ...
  )
  ```

  ```
  create scene (
      use name <scene_name>
      use description <description>
      ...
  )
  ```

  ```
  create action (
      use name <string>
      use description <string>
      has effect <effect_name>      // possibly use some user defined function to apply complicated effects
      ...
  )
  ```

  ```
  create game (
      use entity <entity_name>
      use scene <scene_name>
  )
  ```

  ```
  start game
  ```

Build Tool
----------
We are using sbt (Simple Build Tool) to manage this project. 

Run:

  ```
  sbt run
  sbt compule
  sbt test
  sbt clean
  ```


Nice-to-have features
---------------------
+ convert to native code using latest Java features


Developers
----------
+ Mark Mansi
+ Tianyu Cheng
+ Benjamin Lin
