package com.huehn.initword.bean


data class PersonDetail(var name : String, var age : String)

data class PersonData(var version : String, var person : List<PersonDetail>)


data class RoleDetail(var role : String)

data class RoleData(var version: String, var role: List<RoleDetail>)