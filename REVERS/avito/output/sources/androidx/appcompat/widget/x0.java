package androidx.appcompat.widget;

import Ba1.C13127e;
import android.R;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;

/* compiled from: SwitchCompat$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class x0 implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22661a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22662b;

    /* renamed from: c, reason: collision with root package name */
    public int f22663c;

    /* renamed from: d, reason: collision with root package name */
    public int f22664d;

    /* renamed from: e, reason: collision with root package name */
    public int f22665e;

    /* renamed from: f, reason: collision with root package name */
    public int f22666f;

    /* renamed from: g, reason: collision with root package name */
    public int f22667g;

    /* renamed from: h, reason: collision with root package name */
    public int f22668h;

    /* renamed from: i, reason: collision with root package name */
    public int f22669i;

    /* renamed from: j, reason: collision with root package name */
    public int f22670j;

    /* renamed from: k, reason: collision with root package name */
    public int f22671k;

    /* renamed from: l, reason: collision with root package name */
    public int f22672l;

    /* renamed from: m, reason: collision with root package name */
    public int f22673m;

    /* renamed from: n, reason: collision with root package name */
    public int f22674n;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22662b = propertyMapper.mapObject("textOff", R.attr.textOff);
        this.f22663c = propertyMapper.mapObject("textOn", R.attr.textOn);
        this.f22664d = propertyMapper.mapObject("thumb", R.attr.thumb);
        this.f22665e = propertyMapper.mapBoolean("showText", com.avito.android.R.attr.showText);
        this.f22666f = propertyMapper.mapBoolean("splitTrack", com.avito.android.R.attr.splitTrack);
        this.f22667g = propertyMapper.mapInt("switchMinWidth", com.avito.android.R.attr.switchMinWidth);
        this.f22668h = propertyMapper.mapInt("switchPadding", com.avito.android.R.attr.switchPadding);
        this.f22669i = propertyMapper.mapInt("thumbTextPadding", com.avito.android.R.attr.thumbTextPadding);
        this.f22670j = propertyMapper.mapObject("thumbTint", com.avito.android.R.attr.thumbTint);
        this.f22671k = propertyMapper.mapObject("thumbTintMode", com.avito.android.R.attr.thumbTintMode);
        this.f22672l = propertyMapper.mapObject("track", com.avito.android.R.attr.track);
        this.f22673m = propertyMapper.mapObject("trackTint", com.avito.android.R.attr.trackTint);
        this.f22674n = propertyMapper.mapObject("trackTintMode", com.avito.android.R.attr.trackTintMode);
        this.f22661a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        SwitchCompat switchCompat = (SwitchCompat) obj;
        if (!this.f22661a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22662b, switchCompat.getTextOff());
        propertyReader.readObject(this.f22663c, switchCompat.getTextOn());
        propertyReader.readObject(this.f22664d, switchCompat.getThumbDrawable());
        propertyReader.readBoolean(this.f22665e, switchCompat.getShowText());
        propertyReader.readBoolean(this.f22666f, switchCompat.getSplitTrack());
        propertyReader.readInt(this.f22667g, switchCompat.getSwitchMinWidth());
        propertyReader.readInt(this.f22668h, switchCompat.getSwitchPadding());
        propertyReader.readInt(this.f22669i, switchCompat.getThumbTextPadding());
        propertyReader.readObject(this.f22670j, switchCompat.getThumbTintList());
        propertyReader.readObject(this.f22671k, switchCompat.getThumbTintMode());
        propertyReader.readObject(this.f22672l, switchCompat.getTrackDrawable());
        propertyReader.readObject(this.f22673m, switchCompat.getTrackTintList());
        propertyReader.readObject(this.f22674n, switchCompat.getTrackTintMode());
    }
}
