/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2022 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-FileCopyrightText: 2022 Nextcloud GmbH
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.owncloud.android.datamodel

data class GalleryRow(val files: List<OCFile>, val defaultHeight: Int, val defaultWidth: Int) {
    fun getMaxHeight(): Float {
        return files.map { it.imageDimension?.height ?: defaultHeight.toFloat() }.maxOrNull() ?: 0f
    }
}
