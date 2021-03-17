package com.stepanov.bbf.bugfinder.mutator.transformations

import org.jetbrains.kotlin.psi.KtNamedFunction

import com.stepanov.bbf.bugfinder.util.generateDefValuesAsString
import com.stepanov.bbf.bugfinder.util.getAllPSIChildrenOfType
import com.stepanov.bbf.bugfinder.util.getRandomBoolean
import com.stepanov.bbf.bugfinder.mutator.transformations.Factory.psiFactory as psiFactory


class AddDefaultValueToArg : Transformation() {

    //TODO MAYBE INIT AND REINIT SOME PROPERTIES
    override fun transform() {
        file.getAllPSIChildrenOfType<KtNamedFunction>()
                .asSequence()
                .filter { it.valueParameters.size != 0 && getRandomBoolean(randomConst) }
                .map { it.valueParameters }
                .toList()
                .flatten()
                .forEach { par ->
                    val typeText = par.typeReference?.text ?: return@forEach
                    val defaultKey = typeAndDefaultArgs.keys.find { typeText.startsWith(it) } ?: return@forEach
                    val defaultValue = if (getRandomBoolean() /*false*/) {
                        typeAndDefaultArgs[defaultKey]!!
                    } else {
                        generateDefValuesAsString(typeText)
                    }
                    val newParam = psiFactory.createParameter("${par.name}: $typeText = $defaultValue")
                    checker.replacePSINodeIfPossible(par, newParam)
                }
    }


    private val typeAndDefaultArgs = mapOf(Pair("Int", "1"), Pair("Double", "0.0"), Pair("String", "\"\""),
            Pair("ArrayList", "arrayListOf()"), Pair("List", "listOf()"), Pair("Set", "setOf()"), Pair("Map", "mapOf()"),
            Pair("Array", "arrayOf()"), Pair("Pair", "null to null"))

    private val randomConst = 5
}
