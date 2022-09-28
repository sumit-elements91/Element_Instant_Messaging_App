/*
 * Copyright 2020 The Matrix.org Foundation C.I.C.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.matrix.android.sdk.internal.session.user

import io.realm.kotlin.UpdatePolicy
import org.matrix.android.sdk.internal.database.RealmInstance
import org.matrix.android.sdk.internal.database.model.UserEntity
import org.matrix.android.sdk.internal.database.query.where
import org.matrix.android.sdk.internal.di.SessionDatabase
import javax.inject.Inject

internal interface UserStore {
    suspend fun createOrUpdate(userId: String, displayName: String? = null, avatarUrl: String? = null)
    suspend fun updateAvatar(userId: String, avatarUrl: String? = null)
    suspend fun updateDisplayName(userId: String, displayName: String? = null)
}

internal class RealmUserStore @Inject constructor(@SessionDatabase private val realmInstance: RealmInstance) : UserStore {

    override suspend fun createOrUpdate(userId: String, displayName: String?, avatarUrl: String?) {
        realmInstance.write {
            val userEntity = UserEntity().apply {
                this.userId = userId
                this.displayName = displayName ?: ""
                this.avatarUrl = avatarUrl ?: ""
            }
            copyToRealm(userEntity, UpdatePolicy.ALL)
        }
    }

    override suspend fun updateAvatar(userId: String, avatarUrl: String?) {
        realmInstance.write {
            UserEntity.where(this, userId).first().find()?.let {
                it.avatarUrl = avatarUrl ?: ""
            }
        }
    }

    override suspend fun updateDisplayName(userId: String, displayName: String?) {
        realmInstance.write {
            UserEntity.where(this, userId).first().find()?.let {
                it.displayName = displayName ?: ""
            }
        }
    }
}
