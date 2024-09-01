plugins {
    alias(libs.plugins.pgt.root)
    alias(libs.plugins.kotlin) apply false
}

preprocess {
    strictExtraMappings.set(true)

    val fabric12004 = createNode("1.20.4-fabric", 12004, "yarn")
    val fabric12006 = createNode("1.20.6-fabric", 12006, "yarn")

    fabric12006.link(fabric12004)
}
