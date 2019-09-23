package com.openclassrooms.realestatemanager.model

import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 18/09/2019.
 */
class RealEstate(val id: Int, var type: String = "house", var price: String = "unknown", var surface: Int = 0, var room: Int = 0,
                 var description: String = "no description yet", address: String = "unknown", var city: String = "nowhere"
                 , Interess: ArrayList<Interess>, var statut: Statut = Statut(0,"unknown"), var dateEntree: String = "unknown",
                 var dateVente: String = "unknown", var agent: Agent = Agent(0,"No one","no")) {

    var address: String =address
    get() = field +" "+ city

}