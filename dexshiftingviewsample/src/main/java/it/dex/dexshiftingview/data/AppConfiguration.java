/*
 * Copyright 2014-2015 Diego Grancini
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

package it.dex.dexshiftingview.data;

import java.util.ArrayList;
import java.util.List;

/**
 * DexMoveImageView created by Diego on 06/01/2015.
 */
public class AppConfiguration {
    private static AppConfiguration appConfiguration;
    private List<Section> sections = new ArrayList<>();

    private AppConfiguration() {
        sections.add(new Section("DexPagerAdapter"));
        sections.add(new Section("Color Changer", Section.SECTION_TYPE.SUBSECTION, Section.SECTIONS.ADDER));
    }

    public static AppConfiguration get() {
        if (appConfiguration == null)
            appConfiguration = new AppConfiguration();
        return appConfiguration;
    }

    public List<Section> getSections() {
        return sections;
    }
}
