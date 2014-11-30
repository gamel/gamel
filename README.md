GAMEL
=====
GAMEL is a Scala internal DSL to facilitate game making. The gamel Scala extention provides useful abstractions of game code to users, including entities and scenes.

The language
============

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
    name = 'foo
} of 'Foo
```

This code creates a new instance of the entity with the given name of the given type. Entity definitions are final; once an entity is defined, it cannot be changed.

Attributes
----------

Attributes are user-defined properties of an entity or scene. Each attribute is similar to a variable: it is a tuple containing a name (String) and a value (any type). Every entity and scene has a map storing these tuples.

If attributes are assigned during entity definition, they will be the default attributes of every new instance of the entity.
```
define a new entity {
    name = 'Foo
    attributes += (
        "name" -> "value",
        "answer" -> 42
    )
}
```

Instances can add more attributes or overwrite the ones they inherit:
```
define a new entity {
    name = 'Foo
    attributes += (
        "name" -> "value",
    )
}
define a new entity {
    name = 'foo
    attributes += (
        "name" -> "newValue",   //overwrites entry for "name"
        "random" -> 4
    )
} of 'Foo
```

The keyword `set` is used to add a new attribue or overwrite an attribute of an instance, and the keyword `tell` is used to access the attributes of an instance.
```
define a new entity {
    name = 'Foo
}
define a new entity {
    name = 'foo
    attributes += (
        "name" -> "hello"
    )
} of 'Foo
println('foo tell "name")   //prints out "hello"
'foo set ("name", "goodbye")
println('foo tell "name")   //prints out "goodbye"
```

Ownership
---------
Every entity instance is owned by some other entity instance or scene. Instances that are not explicitly owned by another instance are implicitly owned by `nobody`. Multiple ownership is not allowed; that is, an entity instance has only one owner at any given time.

Each entity has a list of other entity instances it owns named `objects`. This list can be specified during entity definition to specify a default list of owned entities. However, this limits the number of instances of this entity to one, since multiple ownership is not allowed.

```
define a new entity {
    name = 'Foo
}
define a new entity {
    name = 'Bar
    objects += ('foo)
}
create a new instance {
    name = 'foo
} of 'Foo
create a new instance {
} of 'Bar
```

In this example, we can only create one `'Bar` at a time, since each `'Bar` owns `'foo`.

In addition, the creation of an instance can specify a list of objects for that particular instance. This allows creation of multiple instances of the same type, since they have mutually exclusive object lists.

```
define a new entity {
    name = 'Foo
}
define a new entity {
    name = 'Bar
}
create a new instance {
    name = 'foo
} of 'Foo
create a new instance {
    name = 'bar1
    objects += ('foo)
} of 'Bar
create a new instance {
    name = 'bar2
} of 'Bar
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
create a new instance {
    name = 'foo
} of 'Foo
create a new instance {
    name = 'bar
} of 'Bar
create a new instance {
    name = 'baz
} of 'Baz

'nobody gives 'foo to 'bar // bar now owns foo
'bar gives 'foo to 'baz    // baz now owns foo
```

If an entity A tries to give an entity B to some other entity, and A does not own B, an exception is thrown. If A does own B, and something causes the transfer of ownership to fail, A retains ownership of B. Ownership is not transitive.

To check if an instance or scene owns another instance, use the `has` keyword.
```
if('foo has 'bar){
    // case foo owns bar
} else {
    // case foo doesn't own bar
}

Actions
-------
An entity also has a list of actions that it can handle. As with `objects`, `actions` can be declared in a definition or creation construct. Unlike, `objects`, though, multiple instances or entity types can share the same action. An `action` defines an action that an entity can handle. An action is handled by calling the function specified in the action definition. An action is triggered manually using a `does ... using` construct, which takes a set of arguments for the action. The action function must be of type `List[Any] => Unit`. The parameters passed to `does ... using` are in this list.

```
// create an action called fooing
define a new action {
    name = 'fooing
    action = <function>
}
// create a Foo called foo
create a new instance {
    name = 'foo
    actions += ('fooing)
} of 'Foo
'foo does 'fooing using (<parameters>) // trigger the fooing action of Foo
                                       // on the entity instance foo
```

Optionally, an `action` can specify a condition function. During the execution of the game, whenever the condition returns true, the action is triggered. The condition function must be of type `Unit => Boolean`.

```
// create an action called fooing
define a new action {
    name = 'fooing
    action = <function>
    condition = <function>
}
// create a Foo called foo
create a new instance {
    name = 'foo
    actions += ('fooing)
} of 'Foo
```

Scenes
------
A `scene` defines a scene in the game. It is designed to give the user freedom to implement the interface/frontend. A scene is very similar to an entity, but they cannot be defined, only created.

A `scene` is declared with the following syntax

```
// create a scene called Scene1
create a new scene {
    name = 'Scene1
}
```

Note that this does not declare a "scene-type", but instantiates a scene, ready to use.

Scenes can also have ownership of entities, but not other scenes.

```
define a new entity {
    name = 'Foo
}
create a new instance {
    name = 'foo
} of 'Foo
create a new scene {
    name = 'Bar
    objects += ('foo)
}
```

Also, scenes can receive and transfer ownership of entities:

```
define a new entity {
    name = 'Foo
}
create a new instance {
    name = 'foo1
} of 'Foo
create a new instance {
    name = 'foo2
} of 'Foo
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
    action = <function>
}
create a new scene {
    name = 'Foo
    actions += ('fooing)
}
'Foo does 'fooing using (<parameters>)
```

Scenes can also have attributes:

```
create a new scene {
    name = 'Foo
    atrributes += (
        "name" -> "Hello World!",
        "answer" -> 42
    )
}
```

The game object
---------------
In order to specify the beginning state and general properties of the game, a special `game` object must be created. Here, fields for the name, description, resolution, starting scene, and FPS of the game are provided. FPS defaults to 30 if omitted, but all other fields must specified.

```
creat a new scene {
    name = 'start
}
create a new game {
    name = "Hello World Adventure"
    description = "Hello World!"
    resolution = (1024, 768)
    startScene = 'start
    fps = 30
}
```

The game is started with the `start` keyword.

```
start game
```

Only one game object should be specified per game.

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
    name = '<name>
    objects += (<instances>)
    actions += (<actions>)
} of '<type>
```

Trigger an action
```
'<instance or scene> does '<action> using (<parameters>)
```

Changing ownership of an entity
```
'<instance or scene> gives '<instance> to '<instance>
nobody gives '<instance> to '<instance>
```

Create a scene
```
create a new scene {
    name = '<name>
    objects += (<instances>)
    actions += (<actions>)
}
```

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
+ ownership checking (i.e. if 'foo has 'bar then ...)
+ arrays of instances or scenes
+ full compatibility with Java

Developers
----------
+ Tianyu Cheng
+ Benjamin Lin
+ Mark Mansi
