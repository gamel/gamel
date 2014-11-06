import scala.collection.mutable.{Map, HashMap}

// contains global/shared storage for gamel runtime
object globals {

    //This object stores
    //  - declared entity types
    //      - permanent and transfered ownership
    //      - actions
    //  - declared entities
    //  - declared scenes
    //      - permanent and transfered ownership
    //      - transitions
    //  - nowhere
   
    val nowhere = new HashMap[Symbol, Entity]
}

// a property, such as HasObj and ActionObj
sealed trait Property

// represents a "has" construct
case class HasObj(t: Symbol) extends Property{
    var entity_type = t
    var entity_name = 'Placeholder
    def > (n: Symbol): HasObj = {
        this.entity_name = n
        this
    }
}

// represents an entity-type
class Entity(t: Symbol) {
    def entity_type = t
    def as(inner: List[Property]) = {
        println ("defininging an entity-type with properties")
    }
    def called(n: Symbol) = {
        println ("instantiating an entity with type")
    }
}

// represents a scene instantiation
class Scene(n: Symbol) {
    def scene_name = n
    def as(inner: List[Property]) = {
        //check that all properties are only HasObj
        println ("creating a scene with has-es")
    }
}

// handles the "define entity" construct
object define {
    def entity (thing: Entity): Entity = {
        println ("defining an entity")
        thing
    }
}

// handles the "create" constructs
object create {
    def scene (thing: Scene): Scene = {
        println ("creating a scene")
        thing
    }
    def entity (thing: Entity): Entity = {
        println ("instantiating an entity")
        thing
    }
}

// handles the "has" construct
object has {
    def - (s: Symbol): HasObj = new HasObj(s)
}

// required for various gamel constructs
// must be imported by user code:
// import gamel._
object gamel {
    implicit def symbolToEntity(s: Symbol): Entity = new Entity(s)
    implicit def symbolToScene(s: Symbol): Scene = new Scene(s)
    def <(owned: Property*): List[Property] = List(owned:_*)
}
