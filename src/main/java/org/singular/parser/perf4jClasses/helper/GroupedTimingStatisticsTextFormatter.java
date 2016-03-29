/* Copyright (c) 2008-2009 HomeAway, Inc.
 * All rights reserved.  http://www.perf4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.singular.parser.perf4jClasses.helper;

import org.singular.parser.perf4jClasses.GroupedTimingStatistics;

/**
 * GroupedTimingStatisticsFormatter that simply returns the toString() value of the GroupedTimingStatistics instance,
 * with a newline appended.
 *
 * @author Alex Devine
 */
public class GroupedTimingStatisticsTextFormatter implements GroupedTimingStatisticsFormatter {
    public String format(GroupedTimingStatistics stats) {
        return stats.toString() + MiscUtils.NEWLINE;
    }
}