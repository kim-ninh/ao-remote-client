package com.ninhhk.aoremote.database;

import android.provider.BaseColumns;

public final class IRRemoteDbSchema {
    private IRRemoteDbSchema() {
    }

    public static final class RemoteTable {
        public static final String NAME = "Remote";

        public static final class Cols implements BaseColumns {
            // _id is PK
            public static final String NAME = "name";
            public static final String IS_TEMPLATE = "isTemplate";
            public static final String BRAND = "brand";     // FK
            public static final String TYPE = "type";       // FK
        }
    }

    public static final class ButtonTable {

        public static final String NAME = "Button";

        public static final class Cols implements BaseColumns {
            // _id is PK
            public static final String NAME = "name";
            public static final String CODE = "code";
            public static final String REMOTE = "remote";  // FK
        }
    }

    public static final class BrandTable {
        public static final String NAME = "Brand";

        public static final class Cols {
            public static final String NAME = "name";   // PK
        }
    }

    public static final class TypeTable {

        public static final String NAME = "Type";

        public static final class Cols {

            public static final String NAME = "name";   // PK
        }
    }
}
