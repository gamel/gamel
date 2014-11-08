import gamel._
//import gamel.gamel_defs._

object GamelCode extends App {

    define entity 'Tianyu as (
        has - 'Foo > 'foo,
        has - 'Bar > 'bar,
        action - 'Fall := () 
    ) 

    create scene 'Room as (
        has - 'Fizz > 'buzz
    )

    create entity 'Tianyu called 'tianyu

}
