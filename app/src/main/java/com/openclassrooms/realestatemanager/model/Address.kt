package com.openclassrooms.realestatemanager.model

import java.io.Serializable

/**
 * Created by Debruyckère Florian on 27/09/2019.
 */
class Address(val address : String, val appartement : String = "null", val city : String, val country: String) : Serializable