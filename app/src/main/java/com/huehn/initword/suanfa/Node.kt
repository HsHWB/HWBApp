package com.huehn.initword.suanfa

import java.util.*

class Node {
    var value = 0
    var children : Vector<Node>? = null

    constructor() {}

    constructor(value: Int) {
        this.value = value
    }

    constructor(value: Int, _children: Vector<Node>) {
        this.value = value
        this.children = _children;
    }
};