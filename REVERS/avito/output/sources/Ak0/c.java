package AK0;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.adjust.sdk.Constants;
import com.avito.android.remote.model.category_parameters.BooleanParameter;
import com.avito.android.remote.model.category_parameters.IntParameter;
import com.google.gson.Gson;
import java.util.Map;
import java.util.Set;
import kotlin.G0;
import kotlin.Metadata;
import kotlin.jvm.internal.s0;
import uu.C49113a;

/* compiled from: DbPreferencesMigrator.kt */
@s0
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001¨\u0006\u0002"}, d2 = {"LAK0/c;", "", "_avito-discouraged_persistence_core"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    @Y61.k
    public final SQLiteDatabase f296a;

    /* renamed from: b, reason: collision with root package name */
    @Y61.k
    public final Gson f297b;

    /* renamed from: c, reason: collision with root package name */
    @Y61.k
    public final C49113a f298c = new C49113a();

    public c(@Y61.k Context context, @Y61.k SQLiteDatabase sQLiteDatabase, @Y61.k Gson gson, @Y61.k j jVar) {
        this.f296a = sQLiteDatabase;
        this.f297b = gson;
    }

    public final void a(@Y61.k String str, @Y61.k Map map) {
        C49113a c49113a = this.f298c;
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sQLiteDatabase = this.f296a;
        sQLiteDatabase.beginTransaction();
        try {
            for (Map.Entry entry : map.entrySet()) {
                contentValues.clear();
                Object value = entry.getValue();
                String strL = value instanceof Set ? this.f297b.l(value) : String.valueOf(value);
                c49113a.getClass();
                contentValues.put("key", (String) entry.getKey());
                contentValues.put("value", strL);
                contentValues.put("file", str);
                Object value2 = entry.getValue();
                contentValues.put("type", value2 instanceof Integer ? IntParameter.TYPE : value2 instanceof Long ? Constants.LONG : value2 instanceof Boolean ? BooleanParameter.TYPE : value2 instanceof Set ? "string_set" : value2 instanceof Float ? "float" : value2 instanceof String ? "string" : "unknown");
                G0 g02 = G0.f406611a;
                sQLiteDatabase.insert("shared_preferences", null, contentValues);
            }
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
        } catch (Throwable th2) {
            sQLiteDatabase.endTransaction();
            throw th2;
        }
    }
}
