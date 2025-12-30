package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatAutoCompleteTextView$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19951h implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22536a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22537b;

    /* renamed from: c, reason: collision with root package name */
    public int f22538c;

    /* renamed from: d, reason: collision with root package name */
    public int f22539d;

    /* renamed from: e, reason: collision with root package name */
    public int f22540e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22537b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22538c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22539d = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22540e = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22536a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        C19953i c19953i = (C19953i) obj;
        if (!this.f22536a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22537b, c19953i.getBackgroundTintList());
        propertyReader.readObject(this.f22538c, c19953i.getBackgroundTintMode());
        propertyReader.readObject(this.f22539d, c19953i.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22540e, c19953i.getCompoundDrawableTintMode());
    }
}
