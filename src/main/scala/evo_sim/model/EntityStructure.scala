package evo_sim.model

import evo_sim.model.EntityStructure.DomainImpl.{Cooldown, DegradationEffect, Effect, Life, MovementStrategy, Velocity}
import evo_sim.model.BoundingBox._
import evo_sim.model.Entities.BaseBlob
import evo_sim.model.EntityBehaviour.SimulableEntity


object EntityStructure {
  trait Domain {
    type Life
    type Velocity
    type DegradationEffect[A] >: A => Life
    type Effect = BaseBlob => Set[SimulableEntity]  //name to be changed
    type Rivals
    type Position
    type MovementStrategy
    type Cooldown
  }

  object DomainImpl extends Domain {
    override type Life = Int
    override type Velocity = Int
    override type DegradationEffect[A] = A => Life
    override type Rivals = Set[SimulableEntity]
    override type Position = Point2D
    override type MovementStrategy = (Intelligent, Rivals) => Position
    override type Cooldown = Int
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
    override def boundingBox: Circle
    def degradationEffect: DegradationEffect[Blob]
  }

  trait Food extends Entity with Living with Effectful {
    override def boundingBox: Triangle
    def degradationEffect: DegradationEffect[Food]
  }

  trait Obstacle extends Entity with Effectful {
    override def boundingBox: Rectangle
  }






  // status
  /*trait BlobWithTemporaryStatus extends Blob

  trait SlowedBlob extends BlobWithTemporaryStatus {
    def slownessCooldown: Cooldown
    def initialVelocity: Velocity
  }

  trait PoisonedBlob extends BlobWithTemporaryStatus {
    def poisonCooldown: Cooldown
  }*/

  trait BlobWithTemporaryStatus {
    def blob: BaseBlob
  }
}
