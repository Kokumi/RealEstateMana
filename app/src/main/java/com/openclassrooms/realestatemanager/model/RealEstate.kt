package com.openclassrooms.realestatemanager.model

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 18/09/2019.
 */
class RealEstate(val id: Int, var type: String, var price: Int, var surface: Int, var room: Int, var description: String, var address: String
                , Interess: ArrayList<Interess>, var statut: Statut, var dateEntree: Date, var dateVente: String, var agent: Agent) {

}