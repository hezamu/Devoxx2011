package org.vaadin.hezamu.devoxx2011

import scala.collection.mutable.ListBuffer

/**
 * A class to hold the state for the Conway's Game of Life world.
 *
 * @author Henri Muurimaa, henri@vaadin.com
 */
class World {
	val cells = new ListBuffer[(Int, Int)]()

	// Init the grid with the r-pentomino shape
	cells ++= List((0, -1), (1, -1), (-1, 0), (0, 0), (0, 1))

	/**
	 * Increment the world by one iteration.
	 */
	def increment = {
		val checked = new ListBuffer[(Int, Int)]()
		val newstate = new ListBuffer[(Int, Int)]()

		// Update all alive cells and their neighbors
		for (coord <- cells) {
			if (!checked.contains(coord)) {
				if (getNewState(coord))
					newstate += coord
				checked += coord
			}

			for (neighbor <- neighbors(coord).filter(!checked.contains(_))) {
				if (getNewState(neighbor))
					newstate += neighbor
				checked += neighbor
			}
		}

		// Update the internal world state
		cells.clear
		cells ++= newstate
	}

	/**
	 * Calculate the new state of a cell according to the game rules.
	 */
	def getNewState(c: (Int, Int)): Boolean = {
		// Figure out how many neighbor cells are alive
		val livingNeighbors = cells.filter(neighbors(c).contains).size

		livingNeighbors == 3 || (livingNeighbors == 2 && cells.contains(c))
	}

	/**
	 * Helper method to get the neighbors of a cell.
	 */
	def neighbors(c: (Int, Int)) = {
		List((c._1 - 1, c._2 - 1), (c._1, c._2 - 1), (c._1 + 1, c._2 - 1),
			(c._1 - 1, c._2), (c._1 + 1, c._2), (c._1 - 1, c._2 + 1),
			(c._1, c._2 + 1), (c._1 + 1, c._2 + 1))
	}
}