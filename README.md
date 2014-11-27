GAMEL
=====
GAMEL is a Scala internal DSL to facilitate game making. The gamel Scala extention provides useful abstractions of game code to users, including entities and scenes.

Currently under development.

The language
============

<<<<<<< HEAD
The exact syntax is prone to change.

Entities
--------

An entity is an object in the game. This could be anything from a character to a box, as the user pleases. An entity can have ownership of other entities. It can also have action handlers, which execute user-defined code when an action is triggered.

To define an entity:
```
// define a Foo entity
define a new entity {
    name = 'Foo
}
```

This type of entity can be instatiated with:
```
// create a Foo entity called foo
create a new instance {
    of('Foo)
    name = 'foo
}
```

This code create a new entity with the given name of the given type.

Ownership
---------
Every entity instance is owned by some other entity instance or scene. Instances that are not explicitly owned by another instance are implicitly owned by `nobody`. Multiple ownership is not allowed; that is, an entity instance has only one owner at any given time.

Each entity has a list of other entity instances it owns named `objects`. This list can added to during entity definition to specify a default list of owned entities. However, this limits the number of instances of this entity to one, since multiple ownership is not allowed.
```
define a new entity {
    name = 'Foo
}
define a new entity {
    name = 'Bar
}
create a new instance {
    of('Foo)
    name = 'foo
}
create a new instance {
    of('Bar)
    objects += ('foo)
}
```

Gamel uses the `gives-to` construct to represent transfer of ownership:
```
define a new entity {
    name = 'Foo
}
define a new entity {
    name = 'Bar
}
define a new entity {
    name = 'Baz
}

// newly created instances are owned by nobody
create a new entity {
    of('Foo)
    name = 'foo
}
create a new entity {
    of('Bar)
    name = 'bar
}
create a new entity {
    of('Baz)
    name = 'baz
}

'nobody gives 'foo to 'bar // bar now owns foo
'bar gives 'foo to 'baz    // baz now owns foo
```

If an entity A tries to give an entity B to some other entity, and A does not own B, an exception is thrown. If A does own B, and something causes the transfer of ownership to fail, A retains ownership of B. Ownership is not transitive.

Actions
-------

The `action` object defines an action that an entity can handle. An entity contains a list of actions it is able to handle. An action is handled by calling the function(s) specified in the action definition. An action is triggered using a `does` construct, which can optionally pass parameters to the functions defined in the action declaration.
```
// create an action called fooing
define a new action {
    name = 'fooing
    action = (<arguments>) => <function_body>
}
// create a Foo called foo
create a new instance {
    of('Foo)
    name = 'foo
    actions += ('fooing)
}
'foo does 'fooing using (<parameters>) // trigger the fooing action of Foo 
                                       // on the entity instance foo
```
If the action is not defined by the entity, an error is printed to the console, and the program attempts to continue. If the action is defined but cannot complete for some reason, an exception is thrown

Scenes
------
A `scene` defines a scene in the game. It is designed to give the user freedom to implement the interface/frontend. A scene is very similar to an entity, with the main difference being that scenes cannot be defined, only created.

A `scene` is declared with the following syntax
```
// create a scene called Scene1
create a new scene {
    name = 'Scene1
}
```

Note that this does not declare a scene-type, but instantiates a scene, ready to use.

Scenes can also have ownership of entities, but not other scenes.
```
define a new action {
    name = 'fooing
    action = (<arguments>) => <function_body>
}
define a new entity {
    name = 'Foo
    actions += ('fooing)
}
create a new instance {
    of('Foo)
    name = 'foo
}
create a new scene {
    name = 'Bar
    objects += ('foo)
}
'foo does 'fooing using (<parameters>)
```

Also, scenes can receive and transfer ownership of entities:
```
define a new entity {
    name = 'Foo
}
create a new instance {
    of('Foo)
    name = 'foo1
}
create a new instance {
    of('Foo)
    name = 'foo2
}
create a new scene {
    name = 'Bar
}

nobody gives 'foo1 to 'Bar // Bar now owns foo1
'Bar gives 'foo1 to 'foo2 // foo2 now owns foo1
```

Like entities, scenes can have actions:
```
define a new action {
    name = 'fooing
    action = (<arguments>) => <function_body>
}
create a new scene {
    name = 'foo
    actions += ('fooing)
}
'foo does 'fooing using (<parameters>)
```

The game object
---------------
In order to specify the beginning state of the game, a special `game` object must be created. Here, fields for the name, description, resolution, starting scene, and fps of the game are provided.
```
creat a new scene {
    name = 'start
}
create a new game {
    name = "Hello World Advanture"
    description = "Hello World!"
    resolution = (1024, 768)
    startScene = 'start
    fps = 30
}
```

List of Language Constructs
----------------------------
Define an entity-type
```
define a new entity {
    name = '<name>
    objects += (<instances>)
    actions += (<actions>)
}
```

Instantiate an entity
```
create a new entity {
    of('<type>)
    name = '<name>
    objects += (<instances>)
    actions += (<actions>)
}
```

Trigger an action
```
'<instance or scene> does '<action> using (<parameters>)
```

Changing ownership of an entity
```
<instance or scene> gives <instance> to <instance>
nobody gives <instance> to <instance>
```

Create a scene
```
create a new scene {
    name = '<name>
    objects += (<instances>)
    actions += (<actions>)
}
```

Todo
----
+ Action Object (Mark)
+ owns relation (if something owns something)
+ Scene (Tianyu)
+ gets ... from ... (Ben)
+ owns (Ben)
+ entity.name/entity->name (Ben)
+ does (Mark)
=======
The syntax and language features have changed, and this README is being updated.
>>>>>>> 964397f3ea63b77ca4cc24cdb75164aa1c3596d7

Build Tool
----------
We are using sbt (Simple Build Tool) to manage this project. 

Run:

  ```
  sbt run
  sbt compile
  sbt test
  sbt clean
  ```

  To watch for changes and compile in real time
  ```
  $ sbt
    ~ compile
  ```


Nice-to-have features
---------------------
+ is
+ convert to native code using latest Java features
+ arrays
+ compatibility with Java
+ tests

Developers
----------
+ Mark Mansi
+ Tianyu Cheng
+ Benjamin Lin
