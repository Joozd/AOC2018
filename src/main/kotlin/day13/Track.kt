package day13

import common.Coordinate

class Track(x: Int, y: Int, val direction: Char): Coordinate(x,y) {
    private var presentCart: Cart? = null

    /**
     * If a collission occurs, it will remove the carts from the track
     */
    fun placeCartOnTrack(cart: Cart): List<Cart>?{
        cart.currentPos.cartDeparted()
        cart.currentPos = this
        presentCart?.let{
            presentCart = null
            return listOf(cart, it).onEach { cart ->
                cart.died()
            }
        }
        presentCart = cart
        cart.placedOnTrack(direction)
        return null
    }

    private fun cartDeparted(){
        presentCart = null
    }
}