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

package com.microsoft.reef.examples.hello;

import com.microsoft.reef.client.LauncherStatus;
import com.microsoft.reef.examples.hellohttp.HelloREEFHttp;
import com.microsoft.reef.runtime.local.client.LocalRuntimeConfiguration;
import com.microsoft.tang.Configuration;
import com.microsoft.tang.exceptions.BindException;
import com.microsoft.tang.exceptions.InjectionException;
import org.junit.Assert;
import org.junit.Test;

public class HelloHttpTest {
  @Test
  public void testHttpServer() throws BindException, InjectionException {

    final Configuration runtimeConfiguration = LocalRuntimeConfiguration.CONF
        .set(LocalRuntimeConfiguration.NUMBER_OF_THREADS, 2)
        .build();

    final LauncherStatus status = HelloREEFHttp.runHelloReef(runtimeConfiguration, 10 * 1000);
    Assert.assertEquals(LauncherStatus.FORCE_CLOSED, status); // must be force closed by timeout
  }
}