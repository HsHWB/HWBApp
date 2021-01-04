package com.huehn.initword.bean


data class PersonDetail(var name : String, var age : String)

data class PersonData(var version : String, var person : List<PersonDetail>)


data class RoleDetail(var role : String)

data class RoleData(var version: String, var data: List<RoleDetail>)

data class MultableData(var personDetail1 : List<PersonDetail>, var personDetail2 : List<PersonDetail>, var roleDetail : List<RoleDetail>)