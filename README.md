GAMEL
=====
GAMEL is a Scala internal DSL to facilitate game making. The gamel Scala extention provides useful abstractions of game code to users, including entities and scenes.

Currently under development.

The language
============

The exact syntax is prone to change.

Entities
--------

An entity is an object in the game. This could be anything from a character to a box, as the user pleases. An entity can have ownership of other entities. It can also have action handlers, which execute user-defined code when an action is triggered.

To define an entity:
```
define entity Foo ( ) // define a Foo entity
```

This type of entity can be instatiated with:
```
create entity Foo foo // create a Foo entity called foo
```

This code create a new entity with the given name of the given type.

Between the parentheses of the define statement, we can give the entity permanent ownership of another entity and define this entity-type's actions. The `has` construct defines that this entity-type has an *instance* of the given type of entity-type with the given name. We can access the owned entity instance with dot notation used on an instance of the owning type, just as in Java or Scala. Note that we cannot access owned instances through the entity-type; they are not "static".
```
define entity Foo () // define the Foo entity
define entity Bar (
    has Foo foo // all Bars have their very own Foo called foo
)
```

The `action` construct defines an action that this entity-type can handle. An entity handles an action by calling the function(s) specified in the action declaration. An action is triggered using a `does` construct, which can optionally pass parameters to the functions defined in the action declaration. If more than one function is specified by the action handler, they are called in the listed order.
```
define entity Foo (
    action fooing = [F] // F is some Scala func
)
create entity Foo foo // create a Foo called foo
foo does fooing (<parameters>) // trigger the fooing action of Foo 
                               // on the entity instance foo
```
If the action is not defined by the entity, an error is printed to the console, and the program attempts to continue.

A final example:
```
define entity Foo ( // define Foo entity
    action fooing = [F]
)
define entity Bar ( // define Bar entity
    has Foo foo
)

create Bar bar // create a Bar, which implicitly create a Foo
bar.foo does fooing (<parameters>) // trigger fooing on bar's Foo
```

Ownership
---------
Every entity is owned by some other entity. Entities that are not explicitly owned by another entity are implicitly owned by `nowhere`. Also, every entity is owned by `anywhere`. With the exception of `anywhere` multiple ownership is not allowed; that is, an entity has only one owner at any given time.

Gamel uses the `get-from` construct to represent transfer of ownership:
```
define entity Foo ( ) 
define entity Bar ( ) 
define entity Baz ( )

create entity Foo foo // newly created instances are owned by nowhere
create entity Bar bar
create entity Baz baz1
create entity Baz baz2

bar gets foo from nowhere // bar now owns foo
baz1 gets foo from bar    // baz1 now owns foo
baz2 gets foo from anywhere    // baz2 now owns foo
```

Entities declared with `has` in an entity definition cannot be reposessed to another. Also, if an entity A tries to get an entity B from another entity C (including nowhere) that does not own A, the `failedToGet` action is triggered on A. Finally, ownership is transitive.

Scenes
------
A `scene` defines a scene in the game. It is designed to give the user freedom to implement the interface/frontend.

A `scene` is declared with the following syntax
```
create scene Scene1 ( ) // create a scene called Scene1
```

Note that this does not declare a scene-type, but instantiates a scene, ready to use.

Scenes can also have permanent ownership of entities, but not other scenes. Scene-ownership is distinct from entity-ownership; an entity can be owned by both a scene and another entity.
```
define entity Foo (
    action fooing = [F]
)
create scene  Bar ( // the Bar scene has a Foo called foo
    has Foo foo
)

Bar.foo does fooing (<parameters>) // trigger fooing on Bar's Foo
```

Also, scenes can receive ownership of entities:
```
define entity Foo ()
create scene  Bar ()

create entity Foo foo
create entity Foo bar
Bar gets foo from nowhere // Bar now owns foo
bar gets foo from Bar     // bar now owns foo
```

A scene can declare `transition`s to adjacent scenes. This has similar syntax to an entity declaring an action. The main difference is that transitions are declared outside of the scene instantiation, after the two adjacent scenes are both declared.
```
create scene Foo ()
create scene Bar ()

transition Foo left Bar = [F] // F is some Scala function
```
This declares a transition for Foo (not Bar) that goes to Bar called `left`. When `left` is "triggered", the functions in the brackets are called in order, just as with actions. To "trigger" a transition, the user must move an entity from one scene to another:
```
define entity Foo ()
create entity Foo foo

create scene Scene1 ()
create scene Scene2 ()

transition Scene1 down Scene2 = [F] // define the down transition between the two scenes

Scene1 gets foo from nowhere // give initial ownership of foo to Scene1
foo goes down (<parameters>) // foo uses the down transition of Scene1

```

By using the `goes` construct, an entity does two things:
(1) the entity attempts to use the given transition on its current owning scene with the given parameters, and 
(2) the entity is given to the scene connected by the transition to the entity's current scene.

If this fails because the transition is not defined for the current scene, the entity's `failedToGo` action is triggered.

Finally, when an entity goes to another scene, everything it owns moves with it.

Gamel in Scala
------------------
Since gamel code is parsed as valid Scala code, user code can embed gamel code. Simply import the gamel package:
```
import gamel._
```

List of Language Constructs
----------------------------
Define an entity-type
```
define entity <type> (
    has <type> <name>
    action <action> = [<functions>]
)
```

Instantiate an entity
```
create entity <type> <name>
```

Trigger an action
```
<entity> does <action> (<parameters>)
```

Access permanently owned entity
```
<entity>.<entity>
```

Reposessing an entity
```
<entity or scene> gets <entity> from <entity or scene>
<entity or scene> gets <entity> from nowhere
<entity or scene> gets <entity> from anywhere
```

Create a scene
```
create scene <scene> (
    has <type> <name>
)
```

Define a transition
```
transition <scene> <name> <scene> = [<functions>]
```

Use a transition
```
<entity> goes <transition> (<parameters>)
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

Developers
----------
+ Mark Mansi
+ Tianyu Cheng
+ Benjamin Lin
