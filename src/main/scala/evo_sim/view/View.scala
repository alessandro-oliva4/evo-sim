package evo_sim.view

import cats.effect.IO
import evo_sim.model.world.World.WorldHistory
import evo_sim.model.world.{Environment, World}

/** Provides the methods that a View necessitates to be used by the simulation */
trait View {

  /** Gets the simulation initial parameters
   *
   * @return a [[cats.effect.IO]] encoding a [[Environment]] container holding the simulation
   *         initial parameters
   */
  def inputReadFromUser(): IO[Environment]

  /** Renders a single simulation frame
   *
   * @param world the world to represent
   * @return a [[cats.effect.IO]] describing this operation
   */
  def rendered(world: World): IO[Unit]

  /** Reports the statistics of the simulation's world
   *
   * @param world the world to report
   * @return a [[cats.effect.IO]] describing this operation
   */
  def resultsShowed(world: WorldHistory): IO[Unit]
}