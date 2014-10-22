/**
 * Copyright (C) 2014 Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microsoft.reef.runtime.common.driver.defaults;

import com.microsoft.reef.driver.task.TaskMessage;
import com.microsoft.wake.EventHandler;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default event handler for TaskMessage: Logging it.
 */
public final class DefaultTaskMessageHandler implements EventHandler<TaskMessage> {

  private static final Logger LOG = Logger.getLogger(DefaultTaskMessageHandler.class.getName());

  @Inject
  public DefaultTaskMessageHandler() {
  }

  @Override
  public void onNext(final TaskMessage taskMessage) {
    LOG.log(Level.INFO, "Received TaskMessage: {0}", taskMessage);
  }
}