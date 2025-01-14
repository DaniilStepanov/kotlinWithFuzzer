/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.konan

import org.jetbrains.kotlin.descriptors.commonizer.ModulesProvider
import org.jetbrains.kotlin.descriptors.commonizer.ModulesProvider.ModuleInfo
import org.jetbrains.kotlin.konan.library.KONAN_STDLIB_NAME
import org.jetbrains.kotlin.library.SerializedMetadata
import org.jetbrains.kotlin.library.metadata.parseModuleHeader
import java.io.File

internal class NativeDistributionModulesProvider(libraries: Collection<NativeLibrary>) : ModulesProvider {
    internal class NativeModuleInfo(
        name: String,
        originalLocation: File,
        val dependencies: Set<String>,
        isCInterop: Boolean
    ) : ModuleInfo(name, originalLocation, isCInterop)

    private val libraryMap: Map<String, NativeLibrary>
    private val moduleInfoMap: Map<String, NativeModuleInfo>

    init {
        val libraryMap = mutableMapOf<String, NativeLibrary>()
        val moduleInfoMap = mutableMapOf<String, NativeModuleInfo>()

        libraries.forEach { library ->
            val manifestData = library.manifestData

            val name = manifestData.uniqueName
            val location = File(library.library.libraryFile.path)
            val dependencies = manifestData.dependencies.toSet()

            libraryMap.put(name, library)?.let { error("Duplicated libraries: $name") }
            moduleInfoMap[name] = NativeModuleInfo(name, location, dependencies, manifestData.isInterop)
        }

        this.libraryMap = libraryMap
        this.moduleInfoMap = moduleInfoMap
    }

    override fun loadModuleInfos(): Collection<ModuleInfo> = moduleInfoMap.values

    override fun loadModuleMetadata(name: String): SerializedMetadata {
        val library = libraryMap[name]?.library ?: error("No such library: $name")

        val moduleHeader = library.moduleHeaderData
        val fragmentNames = parseModuleHeader(moduleHeader).packageFragmentNameList.toSet()
        val fragments = fragmentNames.map { fragmentName ->
            val partNames = library.packageMetadataParts(fragmentName)
            partNames.map { partName -> library.packageMetadata(fragmentName, partName) }
        }

        return SerializedMetadata(
            module = moduleHeader,
            fragments = fragments,
            fragmentNames = fragmentNames.toList()
        )
    }

    companion object {
        fun forStandardLibrary(stdlib: NativeLibrary): ModulesProvider {
            check(stdlib.manifestData.uniqueName == KONAN_STDLIB_NAME)
            return NativeDistributionModulesProvider(listOf(stdlib))
        }

        fun platformLibraries(librariesToCommonize: NativeLibrariesToCommonize): ModulesProvider =
            NativeDistributionModulesProvider(librariesToCommonize.libraries)
    }
}
