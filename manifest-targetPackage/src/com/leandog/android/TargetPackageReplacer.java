package com.leandog.android;
import java.io.*;

import pxb.android.axml.AxmlReader;
import pxb.android.axml.AxmlVisitor;
import pxb.android.axml.AxmlWriter;

public class TargetPackageReplacer {

    private String manifestFile;

    public TargetPackageReplacer(final String manifestFile) {
        this.manifestFile = manifestFile;
    }

    public void replace(final String targetPackage) throws IOException {
        AxmlReader axmlReader = new AxmlReader(getManifestData());
        AxmlWriter axmlWriter = new AxmlWriter();
        axmlReader.accept(new TargetPackageVisitor(axmlWriter, targetPackage));
        save(axmlWriter);
    }

    private byte[] getManifestData() throws IOException {
        InputStream manifestStream = null;
        try {
            manifestStream = new FileInputStream(manifestFile);
            final byte[] data = new byte[manifestStream.available()];
            manifestStream.read(data);
            return data;
        } finally {
            if (null != manifestStream) {
                manifestStream.close();
            }
        }
    }

    private void save(AxmlWriter axmlWriter) throws FileNotFoundException, IOException {
        final OutputStream outputStream = new FileOutputStream(manifestFile);
        outputStream.write(axmlWriter.toByteArray());
        outputStream.close();
    }

    private final class TargetPackageVisitor extends AxmlVisitor {

        private final String targetPackage;

        public TargetPackageVisitor(AxmlVisitor axmlVisitor, final String targetPackage) {
            super(axmlVisitor);
            this.targetPackage = targetPackage;
        }

        @Override
        public NodeVisitor first(String ns, String name) { // manifest
            return new InstrumentationLocator(super.first(ns, name));
        }

        private final class InstrumentationLocator extends NodeVisitor {

            private InstrumentationLocator(NodeVisitor nodeVisitor) {
                super(nodeVisitor);
            }

            @Override
            public NodeVisitor child(String namespace, String name) {
                if (isInstrumentation(name)) {
                    return new InstrumentationAttrVisitor(super.child(namespace, name));
                }

                return super.child(namespace, name);
            }

            private boolean isInstrumentation(String name) {
                return name.equals("instrumentation");
            }
        }

        private final class InstrumentationAttrVisitor extends NodeVisitor {
            private InstrumentationAttrVisitor(NodeVisitor arg0) {
                super(arg0);
            }

            public void attr(String ns, String name, int resourceId, int type, Object obj) {
                if ("http://schemas.android.com/apk/res/android".equals(ns) && name.equals("targetPackage")) {
                    obj = targetPackage;
                }
                
                super.attr(ns, name, resourceId, type, obj);
            }
        }
    }
}