/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2021 Tobias Kaminsky <tobias@kaminsky.me>
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package com.owncloud.android.ui.fragment

import android.Manifest
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.GrantPermissionRule
import com.owncloud.android.AbstractIT
import com.owncloud.android.R
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.activity.ContactsPreferenceActivity
import com.owncloud.android.ui.fragment.contactsbackup.BackupListFragment
import com.owncloud.android.utils.ScreenshotTest
import org.junit.Rule
import org.junit.Test

class BackupListFragmentIT : AbstractIT() {
    @get:Rule
    val testActivityRule = IntentsTestRule(ContactsPreferenceActivity::class.java, true, false)

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_CALENDAR)

    @Test
    @ScreenshotTest
    fun showLoading() {
        val sut = testActivityRule.launchActivity(null)
        val file = OCFile("/")
        val transaction = sut.supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frame_container, BackupListFragment.newInstance(file, user))
        transaction.commit()

        waitForIdleSync()
        screenshot(sut)
    }

    @Test
    @ScreenshotTest
    fun showContactList() {
        val sut = testActivityRule.launchActivity(null)
        val transaction = sut.supportFragmentManager.beginTransaction()
        val file = getFile("vcard.vcf")
        val ocFile = OCFile("/vcard.vcf")
        ocFile.storagePath = file.absolutePath
        ocFile.mimeType = "text/vcard"

        transaction.replace(R.id.frame_container, BackupListFragment.newInstance(ocFile, user))
        transaction.commit()

        waitForIdleSync()
        shortSleep()
        screenshot(sut)
    }

    @Test
    @ScreenshotTest
    fun showCalendarList() {
        val sut = testActivityRule.launchActivity(null)
        val transaction = sut.supportFragmentManager.beginTransaction()
        val file = getFile("calendar.ics")
        val ocFile = OCFile("/Private calender_2020-09-01_10-45-20.ics.ics")
        ocFile.storagePath = file.absolutePath
        ocFile.mimeType = "text/calendar"

        transaction.replace(R.id.frame_container, BackupListFragment.newInstance(ocFile, user))
        transaction.commit()

        waitForIdleSync()
        screenshot(sut)
    }

    @Test
    @ScreenshotTest
    fun showCalendarAndContactsList() {
        val sut = testActivityRule.launchActivity(null)
        val transaction = sut.supportFragmentManager.beginTransaction()

        val calendarFile = getFile("calendar.ics")
        val calendarOcFile = OCFile("/Private calender_2020-09-01_10-45-20.ics")
        calendarOcFile.storagePath = calendarFile.absolutePath
        calendarOcFile.mimeType = "text/calendar"

        val contactFile = getFile("vcard.vcf")
        val contactOcFile = OCFile("/vcard.vcf")
        contactOcFile.storagePath = contactFile.absolutePath
        contactOcFile.mimeType = "text/vcard"

        val files = arrayOf(calendarOcFile, contactOcFile)
        transaction.replace(R.id.frame_container, BackupListFragment.newInstance(files, user))
        transaction.commit()

        waitForIdleSync()
        screenshot(sut)
    }
}
