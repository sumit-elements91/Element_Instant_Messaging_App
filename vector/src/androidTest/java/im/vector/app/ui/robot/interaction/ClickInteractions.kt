/*
 * Copyright (c) 2022 New Vector Ltd
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

package im.vector.app.ui.robot.interaction

import com.adevinta.android.barista.internal.performAction
import com.adevinta.android.barista.internal.util.resourceMatcher
import im.vector.app.ui.robot.interaction.ViewActions.veryLongClick

object ClickInteractions {
    @JvmStatic
    fun veryLongClickOn(resId: Int) {
        resId.resourceMatcher().performAction(veryLongClick())
    }
}
