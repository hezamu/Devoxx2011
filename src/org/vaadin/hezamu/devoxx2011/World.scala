package org.vaadin.hezamu.devoxx2011

import scala.collection.mutable.HashMap

/**
 * A class to hold the state for the Conway's Game of Life world.
 * 
 * @author Henri Muurimaa, henri@vaadin.com
 */
class World {
	val cells = new HashMap[(Int, Int), Int]()
	
	// Init the grid with the r-pentomino shape
	cells += (0, -1) -> 2
	cells += (1, -1) -> 2
	cells += (-1, 0) -> 2
	cells += (0, 0) -> 2
	cells += (0, 1) -> 2

	/**
	 * Increment the world by one iteration.
	 */	
	def increment = {
		val checked = scala.collection.mutable.ListBuffer[(Int, Int)]()
		val newstate = new HashMap[(Int, Int), Int]

		// Update all alive cells and their neighbors
		for (coord <- cells.filter(_._2 > 1).keys) {
			if (!checked.contains(coord)) {
				newstate.put(coord, getNewState(coord))
				checked += coord
			}

			for (neighbor <- neighbors(coord).filter(!checked.contains(_))) {
				newstate.put(neighbor, getNewState(neighbor))
				checked += neighbor
			}
		}

		// Update the internal world state
		cells.clear
		for ((coord, value) <- newstate.filter(_._2 > 0))
			cells.put(coord, value)
	}

	/**
	 * Calculate the new state of a cell according to the game rules.
	 */
	def getNewState(c: (Int, Int)) = {
		val isAlive = cells.getOrElse(c, 0) > 1 // Is the cell currently alive?
		
		// Figure out how many neighbor cells are alive
		val aliveNeighbors = cells.filter(cell => neighbors(c).contains(cell._1) && cell._2 > 1).size

		if (aliveNeighbors == 2) {
			if(isAlive) 2 else 0
		} else if (aliveNeighbors == 3) {
			if(isAlive) 2 else 3
		} else {
			if(isAlive) 1 else 0 // 
		}
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