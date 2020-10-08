package evo_sim.model

import evo_sim.model.EntityStructure.DomainImpl.{DegradationEffect, Effect, Life, MovementStrategy, Velocity}
import evo_sim.model.BoundingBox._
import evo_sim.model.EntityBehaviour.SimulableEntity


object EntityStructure {
  trait Domain {
    type Life
    type Velocity
    type DegradationEffect[A] >: A => Life
    type Effect = Blob => Set[SimulableEntity]  //name to be changed
    type Rivals
    type Position
    type MovementStrategy
  }

  object DomainImpl extends Domain {
    override type Life = Int
    override type Velocity = Int
    override type DegradationEffect[A] = A => Life
    override type Rivals = Set[Entity]
    override type Position = (Double, Double)
    override type MovementStrategy = (Intelligent, Rivals) => Position
  }

  trait Entity {
    def boundingBox: BoundingBox
  }

  trait Living extends Entity {
    def life: Life
  }

  trait Moving extends Entity {
    def velocity: Velocity
  }

  trait Effectful extends Entity {
    def effect: Effect
  }

  trait Perceptive extends Entity {
    def fieldOfViewRadius : Int
  }

  trait Intelligent extends Perceptive with Moving {
    def movementStrategy: MovementStrategy
  }

  trait Blob extends Entity with Living with Moving with Perceptive with Intelligent {
    override def boundingBox: Rectangle
    def degradationEffect: DegradationEffect[Blob]
  }

  trait Food extends Entity with Living with Effectful {
    override def boundingBox: Circle
    def degradationEffect: DegradationEffect[Food]
  }

  trait Obstacle extends Entity with Effectful {
    override def boundingBox: Triangle
  }

}
