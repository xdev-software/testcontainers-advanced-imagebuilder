/*
 * Copyright © 2024 XDEV Software (https://xdev.software)
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
/**
 * This package was forked from the OpenJDK (as of 21.0.7) to resolve a crash/infinite loop when encountering
 * <a href="https://github.com/xdev-software/testcontainers-advanced-imagebuilder/issues/155">
 * recursive NTFS junctions on Windows
 * </a>.
 * <p>
 * <b>Please note:</b>
 * Enabling it also requires adding ``--add-exports java.base/sun.nio.fs=ALL-UNNAMED`` or performance will be
 * impacted by 100x due non-accessible file attributes cache
 * </p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/NTFS_links#Junction_points">NTFS junction</a>
 */
package software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs;
