package AK0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.avito.android.util.C35851n0;
import com.google.gson.Gson;
import kotlin.G0;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;
import uK0.C48914a;
import uu.C49113a;

/* compiled from: SharedPreferencesMigrator.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK0/q;", "", "_avito-discouraged_persistence_core"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class q {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final Gson f317a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final C49113a f318b = new C49113a();

    public q(@Y61.k Context context, @Y61.k SQLiteDatabase sQLiteDatabase, @Y61.k Gson gson, @Y61.k j jVar) {
        this.f317a = gson;
    }

    public final Cursor a(SQLiteDatabase sQLiteDatabase, String str) {
        return sQLiteDatabase.rawQuery(this.f318b.f440320d, new String[]{str});
    }

    @SuppressLint({"ApplySharedPref"})
    public final void b(Cursor cursor, l lVar) {
        C49113a c49113a = this.f318b;
        if (cursor.getCount() > 0) {
            SharedPreferences.Editor editorEdit = lVar.getF316a().edit();
            C48914a c48914aA = C35851n0.a(cursor);
            while (c48914aA.moveToNext()) {
                try {
                    try {
                        c49113a.getClass();
                        String strF = c48914aA.f("value");
                        c(editorEdit, c48914aA.d("key"), c48914aA.d("type"), strF);
                    } catch (Exception e12) {
                        try {
                            c48914aA.close();
                        } catch (Exception unused) {
                        }
                        throw e12;
                    }
                } catch (Throwable th2) {
                    if (0 == 0) {
                        c48914aA.close();
                    }
                    throw th2;
                }
            }
            G0 g02 = G0.f406611a;
            c48914aA.close();
            editorEdit.commit();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:39:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void c(android.content.SharedPreferences.Editor r3, java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r2 = this;
            int r0 = r5.hashCode()
            switch(r0) {
                case -1572742348: goto L66;
                case -891985903: goto L59;
                case 104431: goto L46;
                case 3327612: goto L33;
                case 64711720: goto L1e;
                case 97526364: goto L9;
                default: goto L7;
            }
        L7:
            goto L9d
        L9:
            java.lang.String r0 = "float"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L13
            goto L9d
        L13:
            if (r6 == 0) goto L9d
            float r5 = java.lang.Float.parseFloat(r6)
            r3.putFloat(r4, r5)
            goto L9d
        L1e:
            java.lang.String r0 = "boolean"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L28
            goto L9d
        L28:
            if (r6 == 0) goto L9d
            boolean r5 = java.lang.Boolean.parseBoolean(r6)
            r3.putBoolean(r4, r5)
            goto L9d
        L33:
            java.lang.String r0 = "long"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L3c
            goto L9d
        L3c:
            if (r6 == 0) goto L9d
            long r5 = java.lang.Long.parseLong(r6)
            r3.putLong(r4, r5)
            goto L9d
        L46:
            java.lang.String r0 = "int"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L4f
            goto L9d
        L4f:
            if (r6 == 0) goto L9d
            int r5 = java.lang.Integer.parseInt(r6)
            r3.putInt(r4, r5)
            goto L9d
        L59:
            java.lang.String r0 = "string"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L62
            goto L9d
        L62:
            r3.putString(r4, r6)
            goto L9d
        L66:
            java.lang.String r0 = "string_set"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L6f
            goto L9d
        L6f:
            if (r6 != 0) goto L73
            r5 = 0
            goto L9a
        L73:
            AK0.p r5 = new AK0.p
            r5.<init>()
            java.lang.reflect.Type r5 = r5.getType()
            boolean r0 = r5 instanceof java.lang.reflect.ParameterizedType
            if (r0 == 0) goto L8e
            r0 = r5
            java.lang.reflect.ParameterizedType r0 = (java.lang.reflect.ParameterizedType) r0
            boolean r1 = com.avito.android.util.R1.a(r0)
            if (r1 == 0) goto L8e
            java.lang.reflect.Type r5 = r0.getRawType()
            goto L92
        L8e:
            java.lang.reflect.Type r5 = com.avito.android.util.R1.b(r5)
        L92:
            com.google.gson.Gson r0 = r2.f317a
            java.lang.Object r5 = r0.e(r6, r5)
            java.util.Set r5 = (java.util.Set) r5
        L9a:
            r3.putStringSet(r4, r5)
        L9d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: AK0.q.c(android.content.SharedPreferences$Editor, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
