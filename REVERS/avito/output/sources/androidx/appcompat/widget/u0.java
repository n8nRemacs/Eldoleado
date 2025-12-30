package androidx.appcompat.widget;

import Ba1.C13127e;
import android.R;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;

/* compiled from: SearchView$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class u0 implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22629a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22630b;

    /* renamed from: c, reason: collision with root package name */
    public int f22631c;

    /* renamed from: d, reason: collision with root package name */
    public int f22632d;

    /* renamed from: e, reason: collision with root package name */
    public int f22633e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22630b = propertyMapper.mapInt("imeOptions", R.attr.imeOptions);
        this.f22631c = propertyMapper.mapInt("maxWidth", R.attr.maxWidth);
        this.f22632d = propertyMapper.mapBoolean("iconifiedByDefault", com.avito.android.R.attr.iconifiedByDefault);
        this.f22633e = propertyMapper.mapObject("queryHint", com.avito.android.R.attr.queryHint);
        this.f22629a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        SearchView searchView = (SearchView) obj;
        if (!this.f22629a) {
            throw C13127e.o();
        }
        propertyReader.readInt(this.f22630b, searchView.getImeOptions());
        propertyReader.readInt(this.f22631c, searchView.getMaxWidth());
        propertyReader.readBoolean(this.f22632d, searchView.f22269Q);
        propertyReader.readObject(this.f22633e, searchView.getQueryHint());
    }
}
