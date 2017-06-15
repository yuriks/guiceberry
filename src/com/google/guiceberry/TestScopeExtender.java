/*
 * Copyright (C) 2017 Google Inc.
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

package com.google.guiceberry;

import com.google.inject.Inject;
import java.util.concurrent.Executor;

/**
 * Allows you to extend a {@link TestScope} to other threads not spawned during that test. This is
 * useful if your tests involve persistent of externally managed thread pools, for example. You can
 * then use {@link TestScopeExtender} wrapped around an {@link Executor} or {@link Runnable} to
 * ensure that on-demand injections (such as from Providers) correctly use bindings from the scope.
 *
 * <p>Instances of this class can be obtained from {@link GuiceBerryModule} and then sent to other
 * threads to be used.
 */
public class TestScopeExtender {

  private final GuiceBerryUniverse universe;
  private final TestDescription testDescription;

  @Inject
  TestScopeExtender(GuiceBerryUniverse universe, TestDescription testDescription) {
    this.universe = universe;
    this.testDescription = testDescription;
  }

  /**
   * Activates the {@link TestScoped} bindings from the thread that originally created this {@link
   * TestScopeExtender}.
   *
   * @return A {@link Scope} that can be closed to restore the previous bindings.
   */
  public Scope enter() {
    TestDescription previousTestDescription = universe.currentTestDescriptionThreadLocal.get();
    universe.currentTestDescriptionThreadLocal.set(testDescription);
    return new Scope(previousTestDescription);
  }

  public class Scope implements AutoCloseable {

    private TestDescription previousTestDescription;

    private Scope(TestDescription previousTestDescription) {
      this.previousTestDescription = previousTestDescription;
    }

    @Override
    public void close() {
      universe.currentTestDescriptionThreadLocal.set(previousTestDescription);
    }
  }
}
