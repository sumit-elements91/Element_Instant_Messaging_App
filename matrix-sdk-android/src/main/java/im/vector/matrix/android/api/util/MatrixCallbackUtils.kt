/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.matrix.android.api.util

import im.vector.matrix.android.api.MatrixCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> suspendCallback(block: (MatrixCallback<T>) -> Cancelable) : T = suspendCancellableCoroutine {
    val cancelable = block(object : MatrixCallback<T> {
        override fun onSuccess(data: T) {
            it.resume(data)
        }

        override fun onFailure(failure: Throwable) {
            it.resumeWithException(failure)
        }
    })
    it.invokeOnCancellation {
        cancelable.cancel()
    }
}
