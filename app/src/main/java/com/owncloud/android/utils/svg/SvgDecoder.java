/*
 * Nextcloud - Android Client
 *
 * SPDX-FileCopyrightText: 2014 Google, Inc. All rights reserved.
 * SPDX-License-Identifier: BSD-2-Clause
 *
 * Borrowed from:
 * https://github.com/bumptech/glide/blob/master/samples/svg/src/main/java/com/bumptech/glide/samples/svg/
 * SvgDecoder.java
 */
package com.owncloud.android.utils.svg;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decodes an SVG internal representation from an {@link InputStream}.
 */
public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {
    public SvgDecoder() {
        // empty constructor
    }

    public Resource<SVG> decode(InputStream source, int w, int h) throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            svg.setDocumentViewBox(0, 0, svg.getDocumentWidth(), svg.getDocumentHeight());
            svg.setDocumentWidth("100%");
            svg.setDocumentHeight("100%");
            svg.setDocumentPreserveAspectRatio(PreserveAspectRatio.LETTERBOX);

            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }

    @Override
    public String getId() {
        return "SvgDecoder.com.owncloud.android";
    }
}
