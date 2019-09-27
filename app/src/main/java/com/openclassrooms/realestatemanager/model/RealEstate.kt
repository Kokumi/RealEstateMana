package com.openclassrooms.realestatemanager.model

import java.io.Serializable
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 18/09/2019.
 */
class RealEstate(val id: Int, var type: String = "house", var price: Price = Price(0,false),
                 var surface: Int = 0, var room: Int = 0,
                 var description: String = "no description yet",var address: Address = Address("nowhere",city = "somewhere", country = "elsewere")
                 , Interess: ArrayList<Interess>, var statut: String = "unknown", var dateEntree: String = "unknown",
                 var dateVente: String = "unknown", var agent: Agent = Agent(0,"No one","no")) : Serializable {
}