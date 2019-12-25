package com.example.rsp.model

/**
 * Rock, Scissors and Paper. Three state game.
 * The winning constraint is represented by following loop.
 * Rock > Scissors > Paper > Rock
 * If you need to add/apply more states to this game,
 * just add those states and update lower and higher states of each state.
 */
enum class Player {
    ROCK {
        override fun getLower(): List<Player> {
            return listOf(SCISSORS)
        }

        override fun getHigher(): List<Player> {
            return listOf(PAPER)
        }
    },
    SCISSORS {
        override fun getLower(): List<Player> {
            return listOf(PAPER)
        }

        override fun getHigher(): List<Player> {
            return listOf(ROCK)
        }
    },
    PAPER {
        override fun getLower(): List<Player> {
            return listOf(ROCK)
        }

        override fun getHigher(): List<Player> {
            return listOf(SCISSORS)
        }
    };

    abstract fun getLower(): List<Player>
    abstract fun getHigher(): List<Player>

    fun isWon(opposite: Player): Int {
        return when {
            this == opposite -> 0 //DRAW
            this.getLower().contains(opposite) -> 1 // You Won
            else -> -1 //You lose
        }
    }
}