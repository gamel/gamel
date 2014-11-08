import scala.collection.mutable.{Map, HashMap}

/**
 * The gamel package object defines the syntax as a DSL,
 * along with needed definitions and implicit conversions
 *
 * The other files in the package define the internal
 * representation of the gamel constructs
 */
package object gamel {

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

    // handles the "action" construct
    object action {
        def - (s: Symbol): ActionObj = new ActionObj(s)
    }

    // Some implicit conversions
    implicit def symbolToEntity(s: Symbol): Entity = new Entity(s)
    implicit def symbolToScene(s: Symbol): Scene = new Scene(s)

}
